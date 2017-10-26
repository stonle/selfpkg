/**   
* 
*/
package com.ls.service.impl;

import java.util.List;
import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.cenum.PKGStatusEnum;
import com.ls.cenum.PkgPaymentEnum;
import com.ls.dao.CityDAO;
import com.ls.dao.DistrictDAO;
import com.ls.dao.OrderDao;
import com.ls.dao.PackageDao;
import com.ls.dao.ProvinceDao;
import com.ls.dao.SysFileDao;
import com.ls.domain.BaseAddress;
import com.ls.domain.City;
import com.ls.domain.District;
import com.ls.domain.OrderInfo;
import com.ls.domain.PackageInfo;
import com.ls.domain.Province;
import com.ls.domain.SysFileInfo;
import com.ls.http.AppVersionVo;
import com.ls.service.OrderService;
import com.ls.util.Page;
import com.ls.util.Utils;
import com.ls.wx.WXConfigUtil;
import com.ls.wx.WXPayCommonUtil;

/** 
* @ClassName: OrderServiceImpl 
* @Description: 
* @author huanglei
* @date 2017年9月2日 上午11:11:40 
* @version V1.0    
*/
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderInfo, String> implements OrderService{
	
	@Autowired
	private OrderDao OrderDaoImpl;
	
	@Autowired
	private CityDAO cityDaoImpl;
	
	@Autowired
	private DistrictDAO districtDAOImpl;
	
	@Autowired
	private ProvinceDao provinceDaoImpl;
	
	@Autowired
	private PackageDao packageDaoImpl;
	
	@Autowired
	private SysFileDao sysFileDaoImpl;
	
	@Override
	public void addOrderInfo(OrderInfo orderInfo) {
	   //生成包裹编号
		orderInfo.setPkgNum(Utils.getRandomNum(16));
	   //生成寄件码
		orderInfo.setSendCode(Utils.getRandomNum(6));
	   //下单时间设置
		orderInfo.setCreateTime(Utils.getCurrentTime());
	   //状态设置  ;0:下单
		orderInfo.setStatus(PKGStatusEnum.NO_INPUT.getIndex());
		//支付状态设置为1
		orderInfo.setPayMent(PkgPaymentEnum.NO_PAYMENT.getIndex());
		OrderDaoImpl.saveObject(orderInfo);
	}
    /**
     * 取消订单；把状态设置为2
     */
	@Override
	public void deleteOrderInfo(String pkgId) {
		OrderInfo orderInfo = OrderDaoImpl.getObjectById(OrderInfo.class, pkgId);
		orderInfo.setStatus(PKGStatusEnum.CANCEL.getIndex());
	}

	@Override
	public List<OrderInfo> queryOrderInfos(String memberId,Page page) {
        //获取order对象
		List<OrderInfo> orderList = OrderDaoImpl.queryOrderInfos(memberId,page);
		//通过orderId查找称重的对象
		for(OrderInfo or:orderList){
			PackageInfo pcInfo = packageDaoImpl.getPckInfoByPkgNum(or.getPkgNum());
			if(pcInfo != null){
				or.setPayMoney(pcInfo.getPaymentAmount());
				or.setWeight(pcInfo.getWeight());
			}
		
		}
		return orderList;
	}
	@Override
	public BaseAddress queryBaseAddress() {
		List<City> citys = cityDaoImpl.getAllObjects(City.class);
		List<District> distincts = districtDAOImpl.getAllObjects(District.class);
		List<Province> provinces = provinceDaoImpl.getAllObjects(Province.class);
		BaseAddress baseAddress = new BaseAddress();
		baseAddress.setCitys(citys);
		baseAddress.setDistricts(distincts);
		baseAddress.setProvinces(provinces);
		return baseAddress;
	}
	@Override
	public int getTotalCount(String memberId) {
		return OrderDaoImpl.getTotalCount(memberId);
	}

	@Override
	public int getTotalCountByPhone(String phone) {
	
		return OrderDaoImpl.getTotalCountByPhone(phone);
	}
    
	@Override
	public AppVersionVo checkProgramVersion(String curVersion) {
		SysFileInfo sysFileInfo = sysFileDaoImpl.getNweSysFileInfo();
		AppVersionVo appVersionVo = new AppVersionVo();
		//版本相同说明是最新版本
		if(!curVersion.equals(sysFileInfo.getFileVersion())){
			
			appVersionVo.setNewVersion(sysFileInfo.getFileVersion());
			appVersionVo.setNote(sysFileInfo.getRemark());
			appVersionVo.setUrl(sysFileInfo.getFileUrl());
		}
		return appVersionVo;
	}
	@Override
	public String checkOrderInfoPayMent(SortedMap<Object, Object> packageParams) {
		String resXml = "";
		if (WXPayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
			if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
				// 这里是支付成功
				////////// 执行自己的业务逻辑////////////////
				String mch_id = (String) packageParams.get("mch_id"); // 商户号
				//String openid = (String) packageParams.get("openid"); // 用户标识
				String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号
				//String total_fee = (String) packageParams.get("total_fee");
				//String transaction_id = (String) packageParams.get("transaction_id"); // 微信支付订单号
				String trade_type = (String) packageParams.get("trade_type");// JSAPI、NATIVE、APP
				// 查询订单 根据订单号查询订单 GoodsTrade -订单实体类
				PackageInfo trade = packageDaoImpl.getPckInfoByPkgNum(out_trade_no);
				OrderInfo order = OrderDaoImpl.getObjectById(OrderInfo.class, out_trade_no);
				//该代码后续修改
				if (!WXConfigUtil.MCH_ID.equals(mch_id) || trade == null /*|| new BigDecimal(total_fee)
						.compareTo(new BigDecimal(trade.getPaymentAmount()).multiply(new BigDecimal(100))) != 0*/) {
					logger.info("支付失败,错误信息：" + "参数错误");
					resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
							+ "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
				} else {
					if (order.getPayMent() != 0) {
						// 订单状态的修改。根据实际业务逻辑执行
						order.setPayMent(0);
						//设置支付时间
						order.setPayMentDate(Utils.getCurrentTime());
						// 0:现金支付；1：微信支付 ；2 ：扫码支付
						if("NATIVE".equals(trade_type)){
							trade.setPayType(2);
						}else{
							trade.setPayType(1);
						}
						resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
								+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

					} else {
						resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
								+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
						logger.info("订单已处理");
					}
				}

			} else {
				logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			}

		} else {
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
			logger.info("通知签名验证失败");
		}
		return resXml;
	}

}
