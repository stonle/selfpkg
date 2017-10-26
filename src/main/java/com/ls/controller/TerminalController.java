/**   
* 
*/
package com.ls.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ls.http.Authen;
import com.ls.http.AuthenOut;
import com.ls.http.BaseTerminal;
import com.ls.http.CheckVo;
import com.ls.http.HargingVo;
import com.ls.http.HeartBeat;
import com.ls.http.HerarBeatOut;
import com.ls.http.HttpBase;
import com.ls.http.HttpConstant;
import com.ls.http.LoginData;
import com.ls.http.OrderInfoOut;
import com.ls.http.OrderStatus;
import com.ls.http.PaymentInfo;
import com.ls.http.PkgInfo;
import com.ls.http.QueryDataVo;
import com.ls.http.QueryListVo;
import com.ls.http.RestartVo;
import com.ls.http.TerResult;
import com.ls.http.TerminalVo;
import com.ls.http.TwoDimensionalInfo;
import com.ls.http.UpdateConfigResultVo;
import com.ls.http.UpdateConfigVo;
import com.ls.http.UpdateResultVo;
import com.ls.http.VersionUpdateVo;
import com.ls.service.OrderService;
import com.ls.service.TerminalService;
import com.ls.util.ExcleEntityUtil;
import com.ls.util.Page;
import com.ls.util.PageUtil;
import com.ls.util.Utils;

import net.sf.json.JSONObject;


/** 
* @ClassName: TerminalController 
* @Description: 终端协议接口
* @author huanglei
* @date 2017年9月8日 上午9:08:17 
* @version V1.0    
*/
@RestController
@RequestMapping("pages")
public class TerminalController {
	@Autowired
	private TerminalService terminalServiceImpl;
	@Autowired
	private OrderService orderServiceImpl;
	private static Logger logger = LogManager.getLogger(TerminalController.class.getName());
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/terminalInterface.do",method={RequestMethod.POST},produces = {"application/json;charset=UTF-8"})
	public HttpBase terminalInterface(@RequestBody HttpBase htttpBase,HttpServletRequest request){
		if(htttpBase == null){
			return null; 
		}
		//10s之前发起的请求被视为过期，无效请求
		if(System.currentTimeMillis()/1000 - htttpBase.getTimestamp() >= 5){
			return null;
		}
		try{
			if(Utils.authenticationSign(htttpBase)){
				switch (htttpBase.getCode()) {
				    //心跳检测   1
					case HttpConstant.REQ_CHECK_HERATBEAT:
						HeartBeat hearBeat = new HeartBeat();
				    	ExcleEntityUtil<HeartBeat> exc = new ExcleEntityUtil<HeartBeat>(hearBeat);
				    	hearBeat = exc.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
				    	HerarBeatOut herarBeatOut = terminalServiceImpl.checkHeartbeat(hearBeat);
				    	htttpBase.setData(herarBeatOut);
						return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_CHECK_HERATBEAT,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//登录校验  2
					case HttpConstant.REQ_LOGIN:
					    LoginData loginData = new LoginData();
					    ExcleEntityUtil<LoginData> exc1 = new ExcleEntityUtil<LoginData>(loginData);
					    loginData = exc1.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
						OrderInfoOut orderInfoOut = terminalServiceImpl.loginTerminal(loginData);
						htttpBase.setData(orderInfoOut);
						return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_LOGIN,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//身份校验  4
					case HttpConstant.REQ_AUTH:
					    Authen authen = new Authen();
					    ExcleEntityUtil<Authen> exc2 = new ExcleEntityUtil<Authen>(authen);
					    authen = exc2.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    AuthenOut authenOut = terminalServiceImpl.checkAuthentication(authen);
					    htttpBase.setData(authenOut);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_AUTH,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//包裹校验   5
					case HttpConstant.REQ_PKG_CHEKC:
						CheckVo checkVo = new CheckVo();
					    ExcleEntityUtil<CheckVo> exc3 = new ExcleEntityUtil<CheckVo>(checkVo);
					    checkVo = exc3.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    BaseTerminal authenOut1 = terminalServiceImpl.checkPkg(checkVo);
					    htttpBase.setData(authenOut1);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PKG_CHEKC,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//计费  6
					case HttpConstant.REQ_PKG_WEIGH:
						PkgInfo pkgInfo = new PkgInfo();
					    ExcleEntityUtil<PkgInfo> exc4 = new ExcleEntityUtil<PkgInfo>(pkgInfo);
					    pkgInfo = exc4.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    HargingVo hargingVo = terminalServiceImpl.updatePaymentInformation(pkgInfo);
					    htttpBase.setData(hargingVo);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PKG_WEIGH,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//支付  8
					case HttpConstant.REQ_PANYMENT:
						PaymentInfo paymentInfo = new PaymentInfo();
					    ExcleEntityUtil<PaymentInfo> exc5 = new ExcleEntityUtil<PaymentInfo>(paymentInfo);
					    paymentInfo = exc5.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    AuthenOut authenOut2 = terminalServiceImpl.savePkgInfoPayMent(paymentInfo);
					    htttpBase.setData(authenOut2);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PANYMENT,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//体积、重量检验（第二次获取体积）7
					case HttpConstant.REQ_CKECK_WEIGH:
						PkgInfo pkgInfo2 = new PkgInfo();
					    ExcleEntityUtil<PkgInfo> exc6 = new ExcleEntityUtil<PkgInfo>(pkgInfo2);
					    pkgInfo2 = exc6.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    AuthenOut authenOut3 = terminalServiceImpl.checkPkgInfo(pkgInfo2);
					    htttpBase.setData(authenOut3);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_CKECK_WEIGH,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//获取支付二维码 8
					case HttpConstant.REQ_PAY_CODE:
						BaseTerminal baseTerminal1 = new BaseTerminal();
					    ExcleEntityUtil<BaseTerminal> exc7 = new ExcleEntityUtil<BaseTerminal>(baseTerminal1);
					    baseTerminal1 = exc7.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    TwoDimensionalInfo twoDimensionalInfo = terminalServiceImpl.updateTwoDimensional(baseTerminal1,request);
					    htttpBase.setData(twoDimensionalInfo);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PAY_CODE,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					 // 第一获取体积 3
					case HttpConstant.REQ_GET_FIRST_VOLUME:
						PkgInfo pkgInfo3 = new PkgInfo();
					    ExcleEntityUtil<PkgInfo> exc8 = new ExcleEntityUtil<PkgInfo>(pkgInfo3);
					    pkgInfo3 = exc8.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    AuthenOut authenOut4 = terminalServiceImpl.savePkgInfo(pkgInfo3);
					    htttpBase.setData(authenOut4);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_GET_FIRST_VOLUME,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//揽收结果通知
					case HttpConstant.REQ_RESULT_CODE:
						TerResult terResult = new TerResult();
					    ExcleEntityUtil<TerResult> terResultEntity = new ExcleEntityUtil<TerResult>(terResult);
					    terResult = terResultEntity.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    AuthenOut resultCallBack = terminalServiceImpl.updateOrderInfoForStatus(terResult);
					    htttpBase.setData(resultCallBack);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_RESULT_CODE,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//列表查询
					case HttpConstant.REQ_QUERY_LIST:
						QueryListVo queryListVo = new QueryListVo();
					    ExcleEntityUtil<QueryListVo> querexc = new ExcleEntityUtil<QueryListVo>(queryListVo);
					    queryListVo = querexc.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    Page page = PageUtil.createPage(5, orderServiceImpl.getTotalCountByPhone(queryListVo.getPhoneNum()),
					    		queryListVo.getCurPage()+1);
					    List<OrderStatus> orList = terminalServiceImpl.queryOrderList(queryListVo,page);
					    QueryDataVo queryDataVo = new QueryDataVo();
					    queryDataVo.setTerminalNum(queryListVo.getTerminalNum());
					    queryDataVo.setCount(orList.size());
					    queryDataVo.setTotalCount(page.getTotalCount());
					    queryDataVo.setDataList(orList);
					    htttpBase.setData(queryDataVo);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_QUERY_LIST,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//终端程序升级
					case HttpConstant.REQ_PRO_UPDATE:
						VersionUpdateVo versionVo = new VersionUpdateVo();
					    ExcleEntityUtil<VersionUpdateVo> versionEntity = new ExcleEntityUtil<VersionUpdateVo>(versionVo);
					    versionVo = versionEntity.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    //获取更新结果路径
					    UpdateResultVo resultVo = terminalServiceImpl.updateResultVo(versionVo);
					    htttpBase.setData(resultVo);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PRO_UPDATE,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//获取更新配置字段
					case HttpConstant.REQ_UPDATE_CONFIG_FIELD:
						TerminalVo terminalVo = new TerminalVo();
						ExcleEntityUtil<TerminalVo> terminalEntity = new ExcleEntityUtil<TerminalVo>(terminalVo);
						terminalVo = terminalEntity.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
						//获取需要更新的配置字段
						List<UpdateConfigVo> configVos = terminalServiceImpl.quUpdateConfig(terminalVo);
						JSONObject job = new JSONObject();
						job.put("terminalNum", terminalVo.getTerminalNum());
						job.put("count", configVos.size());
						job.put("dataList", configVos);
						htttpBase.setData(job);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_UPDATE_CONFIG_FIELD,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//终端收到命令后，准备更新配置文件
					case HttpConstant.REQ_UPDATE_RESULT:
						UpdateConfigResultVo configResultVo = new UpdateConfigResultVo();
						ExcleEntityUtil<UpdateConfigResultVo> configResultEntity = new ExcleEntityUtil<UpdateConfigResultVo>(configResultVo);
						configResultVo = configResultEntity.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
						//更新保存终端配置文件修改的状态
						UpdateConfigResultVo result = terminalServiceImpl.updateConfigStatus(configResultVo);
						htttpBase.setData(result);
						return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_UPDATE_RESULT,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					//终端收到重启命令，准备重启
					case HttpConstant.REQ_RESTART:
						TerminalVo restartVo = new TerminalVo();
					    ExcleEntityUtil<TerminalVo> restartEntity = new ExcleEntityUtil<TerminalVo>(restartVo);
					    restartVo = restartEntity.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    //更新
					    RestartVo res =  terminalServiceImpl.updateTerminalStatus(restartVo);
					    htttpBase.setData(res);
					    return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_RESTART,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					
					 //包裹轮询检测
					case HttpConstant.REQ_POLLING_CHECK:
						BaseTerminal baseTerminal = new BaseTerminal();
					    ExcleEntityUtil<BaseTerminal> baseEntity = new ExcleEntityUtil<BaseTerminal>(baseTerminal);
					    baseTerminal = baseEntity.doMapFormatEntity((LinkedHashMap<String,Object>)htttpBase.getData());
					    //更新
					    AuthenOut AuthenOut =  terminalServiceImpl.pkgPoolingCheck(baseTerminal);
					    htttpBase.setData(AuthenOut);
				        return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_POLLING_CHECK,HttpConstant.SUCCESS_CODE,HttpConstant.SUCCESS_MSG);
					default:
				        return htttpBase;
				}
			
			}else{
				return Utils.respReturnError(htttpBase);
			}
			
		}catch(Exception e){
			logger.error(e);
			//业务定义错误
			if(String.valueOf(HttpConstant.LOGIN_FAIL).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_LOGIN,HttpConstant.LOGIN_FAIL,HttpConstant.LOGIN_FAIL_MSG);
			}
			if(String.valueOf(HttpConstant.CARD_NOT_LIKE).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_AUTH,HttpConstant.CARD_NOT_LIKE,HttpConstant.CARD_NOT_LIKE_MSG);
			}
			if(String.valueOf(HttpConstant.GD_ERROR).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PKG_CHEKC,HttpConstant.GD_ERROR,HttpConstant.GD_ERROR_MSG);
			}
			if(String.valueOf(HttpConstant.CHARING_ERROR).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PKG_WEIGH,HttpConstant.CHARING_ERROR,HttpConstant.CHARING_ERROR_MSG);
			}
			if(String.valueOf(HttpConstant.CKECK_PKG_WEIHT).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_CKECK_WEIGH,HttpConstant.CKECK_PKG_WEIHT,HttpConstant.CKECK_PKG_WEIHT_MSG);
			}
			if(String.valueOf(HttpConstant.PANYMENT_ERROR).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PANYMENT,HttpConstant.PANYMENT_ERROR,HttpConstant.PANYMENT_ERROR_MSG);
			}
			if(String.valueOf(HttpConstant.GET_PAY_CODE).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PAY_CODE,HttpConstant.GET_PAY_CODE,HttpConstant.GET_PAY_CODE_MSGT);
			}
			
			if(String.valueOf(HttpConstant.PAYMENT_AMOUNT_ERROR).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PAY_CODE,HttpConstant.PAYMENT_AMOUNT_ERROR,HttpConstant.PAYMENT_AMOUNT_MSG);
			}
			if(String.valueOf(HttpConstant.TER_NOT_EXIST).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_CHECK_HERATBEAT,HttpConstant.TER_NOT_EXIST,HttpConstant.TER_NOT_EXIST_MSG);
			}
			//已支付在次登录异常
			if(String.valueOf(HttpConstant.ALREAD_PAID_CODE).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.REQ_LOGIN,HttpConstant.ALREAD_PAID_CODE,HttpConstant.ALREAD_PAID_CODE_MSG);
			}
			
			//支付异常处理
			if(String.valueOf(HttpConstant.PAYMENT_EXCEPTION_CODE).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_CHECK_HERATBEAT,HttpConstant.PAYMENT_EXCEPTION_CODE,HttpConstant.PAYMENT_EXCEPTION_MSG);
			}
			
			//异常数据
			if((HttpConstant.DATA_ERROR+","+HttpConstant.REQ_CHECK_HERATBEAT).equals(e.getMessage())){
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_CHECK_HERATBEAT,HttpConstant.DATA_ERROR,HttpConstant.DATA_ERROR_MSG);
			}
			//后台服务程序错误
			switch (htttpBase.getCode()) {
			case HttpConstant.REQ_CHECK_HERATBEAT:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_CHECK_HERATBEAT,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);
			case HttpConstant.REQ_LOGIN:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_LOGIN,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);
			case HttpConstant.REQ_AUTH:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_AUTH,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);
			case HttpConstant.REQ_PKG_CHEKC:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PKG_CHEKC,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);
			case HttpConstant.REQ_PKG_WEIGH:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PKG_WEIGH,HttpConstant.DATA_ERROR,HttpConstant.SERVICE_ERROR_MSG);	
			case HttpConstant.REQ_PANYMENT:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PANYMENT,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);
			case HttpConstant.REQ_CKECK_WEIGH:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_CKECK_WEIGH,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);
			case HttpConstant.REQ_PAY_CODE:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PAY_CODE,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);		
			case HttpConstant.REQ_GET_FIRST_VOLUME:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_GET_FIRST_VOLUME,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);		
			case HttpConstant.REQ_QUERY_LIST:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_QUERY_LIST,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);		
			case HttpConstant.REQ_PRO_UPDATE:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_PRO_UPDATE,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);		
			case HttpConstant.REQ_UPDATE_CONFIG_FIELD:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_UPDATE_CONFIG_FIELD,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);		
			case HttpConstant.REQ_UPDATE_RESULT:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_UPDATE_RESULT,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);		
			case HttpConstant.REQ_POLLING_CHECK:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_POLLING_CHECK,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);		
			case HttpConstant.REQ_RESULT_CODE:
				return Utils.getHttpDataForTer(htttpBase,HttpConstant.RSP_RESULT_CODE,HttpConstant.SERVICE_ERROR,HttpConstant.SERVICE_ERROR_MSG);		
			default:
				break;
			}
			return htttpBase;
		}
		
	}

}
