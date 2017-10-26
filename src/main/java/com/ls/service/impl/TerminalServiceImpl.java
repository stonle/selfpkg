/**   
* 
*/
package com.ls.service.impl;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.cenum.DateEnum;
import com.ls.cenum.PKGStatusEnum;
import com.ls.cenum.PkgCheckTypeEnum;
import com.ls.cenum.PkgPaymentEnum;
import com.ls.cenum.PkgPaymentTypeEnum;
import com.ls.cenum.TaskExeEnum;
import com.ls.cenum.TaskRecStateEnum;
import com.ls.cenum.TaskResultEnum;
import com.ls.cenum.TaskTerOperEnum;
import com.ls.dao.DeviceDao;
import com.ls.dao.OrderDao;
import com.ls.dao.PackageDao;
import com.ls.dao.PkgCheckDao;
import com.ls.dao.SysFileDao;
import com.ls.dao.TerTaskDao;
import com.ls.domain.Device;
import com.ls.domain.OrderInfo;
import com.ls.domain.PackageInfo;
import com.ls.domain.PkgCheckInfo;
import com.ls.domain.SysFileInfo;
import com.ls.domain.TerTaskInfo;
import com.ls.http.Authen;
import com.ls.http.AuthenOut;
import com.ls.http.BaseTerminal;
import com.ls.http.CheckVo;
import com.ls.http.HargingVo;
import com.ls.http.HeartBeat;
import com.ls.http.HerarBeatOut;
import com.ls.http.HttpConstant;
import com.ls.http.HttpResultEnum;
import com.ls.http.LoginData;
import com.ls.http.OrderInfoOut;
import com.ls.http.OrderStatus;
import com.ls.http.PaymentInfo;
import com.ls.http.PkgInfo;
import com.ls.http.QueryListVo;
import com.ls.http.RestartVo;
import com.ls.http.TerResult;
import com.ls.http.TerminalVo;
import com.ls.http.TwoDimensionalInfo;
import com.ls.http.UpdateConfigResultVo;
import com.ls.http.UpdateConfigVo;
import com.ls.http.UpdateResultVo;
import com.ls.http.VersionUpdateVo;
import com.ls.service.TerminalService;
import com.ls.util.DateTimeUtil;
import com.ls.util.Page;
import com.ls.util.PaymentUtil;
import com.ls.util.StringUtil;
import com.ls.util.Utils;
import com.ls.wx.WXCommonUtil;
import com.ls.wx.WXConfigUtil;
import com.ls.wx.WXEnum;
import com.ls.wx.WXPayCommonUtil;
import com.ls.wx.WXXMLUtil;

/** 
* @ClassName: TerminalServiceImpl 
* @Description：终端服务接口实现类 
* @author huanglei
* @date 2017年9月8日 上午10:12:01 
* @version V1.0    
*/
@Service
public class TerminalServiceImpl implements TerminalService{

	@Autowired
	private DeviceDao deviceDaoImpl;
	
	@Autowired
	private OrderDao orderDaoImpl;
	
	@Autowired
	private PackageDao packageDaoImpl;
	
	@Autowired
    private TerTaskDao terTaskDaoimpl;
	@Autowired
	private SysFileDao sysFileDaoImpl;
	
	@Autowired
	private PkgCheckDao pkgCheckDaoImpl;
	/**
	 * 心跳检测
	 */
	@Override
	public HerarBeatOut checkHeartbeat(HeartBeat heartBeat) {
		if(heartBeat == null){
			throw new RuntimeException(HttpConstant.DATA_ERROR+","+HttpConstant.REQ_CHECK_HERATBEAT);
		}
		//跟新数据库状态字段   心跳检测终端是否已上线，即投入使用
		Device device = deviceDaoImpl.getObjectById(Device.class, heartBeat.getTerminalNum());
		if(device != null){
			device.setRunDate(System.currentTimeMillis());
		}else{
			throw new RuntimeException(String.valueOf(HttpConstant.TER_NOT_EXIST));
		}
		HerarBeatOut herarBeatOut = new HerarBeatOut();
		herarBeatOut.setTerminalNum(heartBeat.getTerminalNum());
		herarBeatOut.setPkgId(heartBeat.getPkgId());
		//1、根据版本号查询与终端编号，若查询到有对象，则修改状态
		TerTaskInfo devsion = terTaskDaoimpl.getDevVersionByVersion(heartBeat.getTerminalNum(), heartBeat.getVersion());
		if(devsion != null){
			//判断是否执行成功
			devsion.setIsResult(TaskResultEnum.SUCCESS.getIndex());
			terTaskDaoimpl.updateObject(devsion);
		}
		//2、根据终端号查询，看其是否有操作
		TerTaskInfo terTaskInfo = terTaskDaoimpl.getDevVersionByDevNum(heartBeat.getTerminalNum(),true);
        if(terTaskInfo !=null){
        	herarBeatOut.setOperType(terTaskInfo.getOperType());
        }else{
        	//终端没有任务可执行
        	herarBeatOut.setOperType(TaskTerOperEnum.NO_OPERATER.getIndex());
        }
        
        //3：判斷支付結果
        if(StringUtil.isNotEmpty(heartBeat.getPkgId())){
        	 OrderInfo orderInfo = orderDaoImpl.getObjectById(OrderInfo.class, heartBeat.getPkgId());
        	if(orderInfo != null){
        		if(DateTimeUtil.getTimeDifference(orderInfo.getCodeDate(), orderInfo.getPayMentDate())>3){
        			throw new RuntimeException(String.valueOf(HttpConstant.PAYMENT_EXCEPTION_CODE));
        		}
        	    herarBeatOut.setWxPay(orderInfo.getPayMent());
        	}else{
        		herarBeatOut.setWxPay(HttpResultEnum.FAIL.getIndex());
        	}
        }else{
        	herarBeatOut.setWxPay(HttpResultEnum.FAIL.getIndex());
        }
        return herarBeatOut;
	}
	
	/**
	 * 登录验证；根据寄件人的电话和寄件码
	 */
	@Override
	public OrderInfoOut loginTerminal(LoginData loginData) {
		//先判断手机号和取件码时候存在
		OrderInfo orderInfo = orderDaoImpl.ckeckOrder(loginData.getPhoneNum(),loginData.getPsw());
		if(orderInfo ==null){
			throw new RuntimeException(String.valueOf(HttpConstant.LOGIN_FAIL));
		}
		//如果以支付则不能再登录
		if(orderInfo.getPayMent()==0){
			throw new RuntimeException(String.valueOf(HttpConstant.ALREAD_PAID_CODE));
		}
		//返JSON对象组装
		OrderInfoOut orderInfoOut = new OrderInfoOut();
		orderInfoOut.setTerminalNum(loginData.getTerminalNum());
		orderInfoOut.setPkgId(orderInfo.getPkgNum());
		orderInfoOut.setRecvAddr(orderInfo.getRecvPerInfo().getRecvAddr());
		orderInfoOut.setRecvPhone(orderInfo.getRecvPerInfo().getRecvPhone());
		orderInfoOut.setRecvName(orderInfo.getRecvPerInfo().getRecvName());
		orderInfoOut.setSendAddr(orderInfo.getSendPerInfo().getSendAddr());
		orderInfoOut.setSendPhone(orderInfo.getSendPerInfo().getSendPhone());
		orderInfoOut.setSendName(orderInfo.getSendPerInfo().getSendName());
		return  orderInfoOut;
	}
    /**
     * 身份校验
     */
	@Override
	public AuthenOut checkAuthentication(Authen authen) {
	    OrderInfo order = orderDaoImpl.getObjectById(OrderInfo.class, authen.getPkgId());
		if(order == null || !authen.getUserId().equals(order.getMemberInfo().getMemberCard())){
			throw new RuntimeException(String.valueOf(HttpConstant.CARD_NOT_LIKE));
		}
		AuthenOut authenOut = new AuthenOut();
		authenOut.setPkgId(authen.getPkgId());
		authenOut.setResult(HttpResultEnum.SUCCESS.getIndex());
		authenOut.setTerminalNum(authen.getTerminalNum());
		return authenOut;
	}
	
	/**
	 * 包裹视频验视
	 */
	@Override
	public BaseTerminal checkPkg(CheckVo checkVo) {
		
		Device device = deviceDaoImpl.getObjectById(Device.class, checkVo.getTerminalNum());
		device.setVideoNum(checkVo.getCameraId());
		//根据包裹编号查询包裹；若存在则把status设置为0，默认状态为1;
		PkgCheckInfo checkInfo = pkgCheckDaoImpl.getCheckInfoByPkgNum(checkVo.getPkgId());
		if(checkInfo !=null){
			checkInfo.setStatus(DateEnum.DELETE.getIndex());
		}
		//数据库保存该数据
		PkgCheckInfo entity = new PkgCheckInfo();
		entity.setDevNum(checkVo.getTerminalNum());
		entity.setCreateDate(Utils.getCurrentTime());
		//默认未做处理
		entity.setType(PkgCheckTypeEnum.NO_HANDLE.getIndex());
		
		entity.setPkgNum(checkVo.getPkgId());
		entity.setStatus(DateEnum.NO_DELETE.getIndex());
		entity.setCkeckUrl(HttpConstant.VIE_PRE_URL+device.getCloudNum());
		pkgCheckDaoImpl.saveObject(entity);
		BaseTerminal authenOut = new BaseTerminal();
		authenOut.setPkgId(checkVo.getPkgId());

		authenOut.setTerminalNum(checkVo.getTerminalNum()); 
		return authenOut;
	}
    /**
     * 第一次获取体积
     */
	@Override
	public AuthenOut savePkgInfo(PkgInfo pkgInfo) {
		try{
			//判断改包裹是否已称重，若已称重，则修改,根据包裹编号查询，包裹信息
			PackageInfo crepackageInfo = packageDaoImpl.getPckInfoByPkgNum(pkgInfo.getPkgId());
			if(crepackageInfo != null){
                if(pkgInfo.getTerminalNum().equals(crepackageInfo.getDevice().getDevNum())){
                	crepackageInfo.setHeight(pkgInfo.getHeight());
    				crepackageInfo.setLength(pkgInfo.getLength());
    				crepackageInfo.setWeight(pkgInfo.getWeight());
    				crepackageInfo.setWidth(pkgInfo.getWidth());
                }else{
                	crepackageInfo.setStatus(DateEnum.DELETE.getIndex());
        			savePackageInfoExtract(pkgInfo);
                }
			}else{
				savePackageInfoExtract(pkgInfo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		AuthenOut hargingVo = new AuthenOut();
		hargingVo.setPkgId(pkgInfo.getPkgId());
		hargingVo.setTerminalNum(pkgInfo.getTerminalNum());
		hargingVo.setResult(HttpResultEnum.SUCCESS.getIndex());
		//返回数据
		return hargingVo;
	}

	private void savePackageInfoExtract(PkgInfo pkgInfo) {
		PackageInfo packageInfo = new PackageInfo();
		packageInfo.setHeight(pkgInfo.getHeight());
		packageInfo.setLength(pkgInfo.getLength());
		packageInfo.setWeight(pkgInfo.getWeight());
		packageInfo.setWidth(pkgInfo.getWidth());
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setPkgNum(pkgInfo.getPkgId());
		Device device = new Device();
		device.setDevNum(pkgInfo.getTerminalNum());
		packageInfo.setDevice(device);
		packageInfo.setOrderInfo(orderInfo);
		packageInfo.setCreateDate(Utils.getCurrentTime());
		packageInfo.setStatus(DateEnum.NO_DELETE.getIndex());
		packageDaoImpl.saveObject(packageInfo);
	}
    /**
     *支付数据保存
     */
	@Override
	public AuthenOut savePkgInfoPayMent(PaymentInfo paymentInfo) {
	    //根据包裹编号查询，包裹信息
		PackageInfo packageInfo = packageDaoImpl.getPckInfoByPkgNum(paymentInfo.getPkgId());
		packageInfo.setPayType(paymentInfo.getType());
		packageInfo.setRecvMany(paymentInfo.getRecv());
		packageInfo.setBackMany(paymentInfo.getBack());
		//设置支付方式，及支付状态设置
		packageInfo.setPayType(PkgPaymentTypeEnum.XJ_PAY.getIndex());
		OrderInfo orderInfo = packageInfo.getOrderInfo();
		orderInfo.setPayMent(PkgPaymentEnum.PAYMENT.getIndex());
		//返回数据组合
		AuthenOut authenOut = new AuthenOut();
		authenOut.setPkgId(paymentInfo.getPkgId());
		authenOut.setResult(HttpResultEnum.SUCCESS.getIndex());
		authenOut.setTerminalNum(paymentInfo.getTerminalNum()); 
		authenOut.setType(paymentInfo.getType());
		return authenOut;
	}
     
	/**
	 * 第二次获取体积及重量进行对比   （待定）
	 */
	@Override
	public AuthenOut checkPkgInfo(PkgInfo pkgInfo2) {
	    //根据包裹编号查询，包裹信息
	    PackageInfo packageInfo = packageDaoImpl.getPckInfoByPkgNum(pkgInfo2.getPkgId());
	    //体重和重量检验（校验规则待定）
	    if(packageInfo == null 
	    		|| pkgInfo2.getHeight()-packageInfo.getHeight() > 20
	    		|| pkgInfo2.getLength()-packageInfo.getLength() > 20 
	    		|| pkgInfo2.getWidth()-packageInfo.getWidth() > 20 
	    		||pkgInfo2.getWeight() - packageInfo.getWeight() > 50){
	    	throw new RuntimeException(String.valueOf(HttpConstant.CKECK_PKG_WEIHT));
	    }
		AuthenOut authenOut = new AuthenOut();
		authenOut.setPkgId(pkgInfo2.getPkgId());
		authenOut.setResult(HttpResultEnum.SUCCESS.getIndex());
		authenOut.setTerminalNum(pkgInfo2.getTerminalNum()); 
		return authenOut;
	}
	/***
	 * 扫码支付
	 */
	@Override
	public TwoDimensionalInfo updateTwoDimensional(BaseTerminal baseTerminal1,HttpServletRequest request) {
		//根据包裹编号获取订单信息
		PackageInfo packageInfo = packageDaoImpl.getPckInfoByPkgNum(baseTerminal1.getPkgId());
		if(packageInfo == null){
			throw new RuntimeException();
		}
		//微信接口，根据金额及其它信息生成微信二维码URL
		String pay_url = weixin_pay(packageInfo,request);
		//二维码生成时间设置
		packageInfo.getOrderInfo().setCodeDate(Utils.getCurrentTime());
		//数据组装
		TwoDimensionalInfo twoDimensionalInfo = new TwoDimensionalInfo();
		twoDimensionalInfo.setPkgId(baseTerminal1.getPkgId());
		twoDimensionalInfo.setTerminalNum(baseTerminal1.getTerminalNum());
		twoDimensionalInfo.setUrl(pay_url);
		return twoDimensionalInfo;
	}
    
	/**
	 *包裹称重计费
	 */
	@Override
	public HargingVo updatePaymentInformation(PkgInfo pkgInfo) {
		PackageInfo packageInfo = packageDaoImpl.getPckInfoByPkgNum(pkgInfo.getPkgId());
		//根据重量计算费用
		double payment = PaymentUtil.getManyByWeight(pkgInfo.getWeight());
		packageInfo.setPaymentAmount(payment);
		//
		HargingVo hargingVo = new HargingVo();
		hargingVo.setPkgId(pkgInfo.getPkgId());
		hargingVo.setTerminalNum(pkgInfo.getTerminalNum());
		hargingVo.setPrice(payment);
		return hargingVo;
	}
    /**
     * 分页查询
     * 需按时间进行排序
     * 手机号为寄件人手机号
     */
	@Override
	public List<OrderStatus> queryOrderList(QueryListVo queryListVo,Page page) {
		/** 
		 *  默认为5条
		 * 	map.put("totalCount", page.getTotalCount());
		 *  map.put("currentPage", page.getCurrentPage());
		 *  map.put("totalPage", page.getTotalPage());
		 */
		List<OrderInfo> orderInfos = orderDaoImpl.listPackageByPhone(queryListVo.getPhoneNum(),page);
		List<OrderStatus> orderStatus = new ArrayList<OrderStatus>();
		//封装对象传回
		for(OrderInfo order:orderInfos){
			OrderStatus ordetatus = new OrderStatus();
			ordetatus.setPkgId(order.getPkgNum());
			ordetatus.setTerminalNum(queryListVo.getTerminalNum());
			ordetatus.setStatus(order.getState());
			ordetatus.setRecvAddr(order.getRecvPerInfo().getRecvAddr());
			ordetatus.setRecvName(order.getRecvPerInfo().getRecvName());
			ordetatus.setRecvPhone(order.getRecvPerInfo().getRecvPhone());
			ordetatus.setSendAddr(order.getSendPerInfo().getSendAddr());
			ordetatus.setSendName(order.getSendPerInfo().getSendName());
			ordetatus.setSendPhone(order.getSendPerInfo().getSendPhone());
			orderStatus.add(ordetatus);
		}
		return orderStatus;
	}
    
	/**
	 * 获取更新结果,用于终端程序升级
	 */
	@Override
	public UpdateResultVo updateResultVo(VersionUpdateVo v) {
		UpdateResultVo updateResultVo = new UpdateResultVo();
		
		TerTaskInfo devVersion = terTaskDaoimpl.getDevVersionByDevNum(v.getTerminalNum(),true);
		//已接收任务
		devVersion.setIsSend(TaskRecStateEnum.RECEIVED.getIndex());
		//已执行设置、默认终端执行该任务
		devVersion.setStatus(TaskExeEnum.EXECUTED.getIndex());
		//执行时间设置，成功与失败时间
		devVersion.setExecuteTime(Utils.getCurrentTime());
		//根据版本号获取更新信息
		SysFileInfo sysFileInfo = sysFileDaoImpl.getsysFileInfoByVersion(devVersion.getbVersion());
		if(sysFileInfo != null){
			updateResultVo.setHashCode(sysFileInfo.getFileMD5());
			updateResultVo.setTerminalNum(v.getTerminalNum());
			updateResultVo.setUrl(sysFileInfo.getFileUrl());
			updateResultVo.setFileName(sysFileInfo.getFileName());
			updateResultVo.setVersion(sysFileInfo.getFileVersion());	
		}
		return updateResultVo;
	}

    /**
     * 获取需要更新的配置
     */
	@Override
	public List<UpdateConfigVo> quUpdateConfig(TerminalVo terminalVo) {
		List<UpdateConfigVo>  updateConfigVos = new ArrayList<UpdateConfigVo>();
		List<TerTaskInfo> terConfigs = terTaskDaoimpl.queryTerConfigsByDevNum(terminalVo.getTerminalNum(),true);
		for(TerTaskInfo ter:terConfigs){
			UpdateConfigVo updateConfigVo = new UpdateConfigVo();
			updateConfigVo.setConfgKey(ter.getConfigKey());
			updateConfigVo.setConfgValue(ter.getConfigValue());
			updateConfigVo.setType(ter.getConfigType());
			updateConfigVos.add(updateConfigVo);
		}
		return updateConfigVos;
	}
     
	/**
	 * 修改终端配置文件状态回复
	 */
	@Override
	public UpdateConfigResultVo updateConfigStatus(UpdateConfigResultVo configResultVo) {
		List<TerTaskInfo> terConfigs = terTaskDaoimpl.queryTerConfigsByDevNum(configResultVo.getTerminalNum(),true);
		String executeTime = Utils.getCurrentTime();
		for(TerTaskInfo ter:terConfigs){
			ter.setStatus(TaskExeEnum.EXECUTED.getIndex());
			ter.setIsResult(TaskResultEnum.SUCCESS.getIndex());
			ter.setIsSend(TaskRecStateEnum.RECEIVED.getIndex());
			ter.setExecuteTime(executeTime);
		}
		UpdateConfigResultVo updateConfigResultVo = new UpdateConfigResultVo();
		updateConfigResultVo.setResult(HttpResultEnum.SUCCESS.getIndex());
		updateConfigResultVo.setTerminalNum(configResultVo.getTerminalNum());
		return updateConfigResultVo;
	}
	/**
	 * 更新终端状态表 ,重启状态更新
	 * 
	 * 可以确定执行时间，并设置执行成功状态；
	 */
	@Override
	public RestartVo updateTerminalStatus(TerminalVo restartVo) {
		TerTaskInfo terOperation = terTaskDaoimpl.getDevVersionByDevNum(restartVo.getTerminalNum(),true);
		//已执行
		terOperation.setStatus(TaskExeEnum.EXECUTED.getIndex());
		//执行结果
		terOperation.setIsResult(TaskResultEnum.SUCCESS.getIndex());
		//已接收
		terOperation.setIsSend(TaskRecStateEnum.RECEIVED.getIndex());
		//执行时间设置
		terOperation.setExecuteTime(Utils.getCurrentTime());
		RestartVo vo = new RestartVo();
		vo.setRestartType(terOperation.getOperType());
		vo.setTerminalNum(restartVo.getTerminalNum());
		return vo;
	}


	@Override
	public AuthenOut pkgPoolingCheck(BaseTerminal baseTerminal) {
		PkgCheckInfo pkgCheckInfo = pkgCheckDaoImpl.getPkgCheckInfo(baseTerminal);
		AuthenOut authenOut = new AuthenOut();
		authenOut.setPkgId(baseTerminal.getPkgId());
		authenOut.setTerminalNum(baseTerminal.getTerminalNum());
		authenOut.setResult(pkgCheckInfo.getType());
		return authenOut;
	}
	
	/**
	 * 获取扫码支付url,并保存二维码的生成时间；
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static String weixin_pay(PackageInfo packageInfo,HttpServletRequest request) {
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		packageParams.put(WXEnum.APPID.getName(), WXConfigUtil.APPID);
		packageParams.put(WXEnum.MchId.getName(), WXConfigUtil.MCH_ID);
		packageParams.put(WXEnum.NONCE_STR.getName(), WXPayCommonUtil.CreateNoncestr());
		//
		packageParams.put(WXEnum.BODY.getName(), packageInfo.getOrderInfo().getPkgNum());
		
		packageParams.put(WXEnum.OUT_TRADE_NO.getName(),packageInfo.getOrderInfo().getPkgNum());
		//金额转换
		int price = new BigDecimal(packageInfo.getPaymentAmount()).multiply(new BigDecimal(100)).intValue();
		if(price<=0){
	        throw new RuntimeException(String.valueOf(HttpConstant.PAYMENT_AMOUNT_ERROR));
		}
		//packageParams.put(WXEnum.TOTAL_FEE.getName(), String.valueOf(price));
		packageParams.put(WXEnum.TOTAL_FEE.getName(), String.valueOf(1));
		packageParams.put(WXEnum.SPBILL_CREATE_IP.getName(), WXCommonUtil.toIpAddr(request));
		//回调路径
		// 设置回调地址-获取当前的地址拼接回调地址
		String url = request.getRequestURL().toString();
		String domain = url.substring(0, url.length() - 13);
		String notify_url = domain + WXConfigUtil.CALLBACK_REQ_METHOD;
		//测试用
		packageParams.put(WXEnum.NOTIFY_URL.getName(), "http://stonele.xicp.io/selfpkg/pages/appWxNotify.do");
		//正式环境
		//packageParams.put(WXEnum.NOTIFY_URL.getName(), notify_url);
		
		packageParams.put(WXEnum.TRADE_TYPE.getName(), WXEnum.NATIVE.getName());
		String sign = WXPayCommonUtil.createSign(WXEnum.UTF8.getName(), packageParams);
		packageParams.put(WXEnum.SIGN.getName(), sign);
		String requestXML = WXPayCommonUtil.getRequestXml(packageParams);
		String resXml = WXPayCommonUtil.httpsRequest(WXConfigUtil.UNIFIED_ORDER_URL,WXEnum.POST.getName(), requestXML);
		Map map = null;
		try {
			map = WXXMLUtil.doXMLParse(resXml);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return (String) map.get(WXEnum.CODE_URL.getName());
	}
    /**
     * 揽收结果通知
     */
	@Override
	public AuthenOut updateOrderInfoForStatus(TerResult terResult) {
		 //根据包裹编号查询，包裹信息
		PackageInfo packageInfo = packageDaoImpl.getPckInfoByPkgNum(terResult.getPkgId());
		if(packageInfo == null){
			throw new RuntimeException();
		}
		OrderInfo orderInfo = packageInfo.getOrderInfo();
		orderInfo.setStatus(PKGStatusEnum.CATHER.getIndex());
		//返回数据组合
		AuthenOut authenOut = new AuthenOut();
		authenOut.setPkgId(terResult.getPkgId());
		authenOut.setResult(HttpResultEnum.SUCCESS.getIndex());
		authenOut.setTerminalNum(terResult.getTerminalNum()); 
		authenOut.setType(packageInfo.getPayType());
		return authenOut;
	} 

}
