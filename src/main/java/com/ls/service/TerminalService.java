/**   
* 
*/
package com.ls.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.ls.http.Authen;
import com.ls.http.AuthenOut;
import com.ls.http.BaseTerminal;
import com.ls.http.CheckVo;
import com.ls.http.HargingVo;
import com.ls.http.HeartBeat;
import com.ls.http.HerarBeatOut;
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
import com.ls.util.Page;

/** 
* @ClassName: TerminalService 
* @Description: 终端接口服务接口
* @author huanglei
* @date 2017年9月8日 上午10:10:48 
* @version V1.0    
*/
public interface TerminalService{
	/**
	 * 心跳检测
	 * @param jsonObject
	 * @return
	 */
	HerarBeatOut checkHeartbeat(HeartBeat heartBeat);
	/**
	 * 终端登录
	 * @param jsonObject
	 * @return
	 */
	OrderInfoOut loginTerminal(LoginData loginData);
	
	/**
	 * 身份证校验
	 * @param authen
	 * @return
	 */
	AuthenOut checkAuthentication(Authen authen);
	
	/**
	 * 包裹监视接口
	 * @param baseTerminal
	 * @return
	 */
	BaseTerminal checkPkg(CheckVo checkVo);
	
	/**
	 * 保存包裹的重量级体积基本信息
	 * @return
	 */
	AuthenOut savePkgInfo(PkgInfo pkgInfo);
	
	/**
	 * 保存包裹的支付信息
	 * @param paymentInfo
	 * @return
	 */
	AuthenOut savePkgInfoPayMent(PaymentInfo paymentInfo);
	
	/**
	 * 包裹体积、重量校验
	 * @param pkgInfo2
	 * @return
	 */
	AuthenOut checkPkgInfo(PkgInfo pkgInfo);
	
	/**
	 * 获取二维码信息
	 * @return
	 */
	TwoDimensionalInfo updateTwoDimensional(BaseTerminal baseTerminal1,HttpServletRequest request);
	
	/**
	 * 支付价格计算
	 * @param pkgInfo
	 * @return
	 */
	HargingVo updatePaymentInformation(PkgInfo pkgInfo);
	
	/**
	 * 根据条件获取订单列表
	 * @param queryListVo
	 * @return
	 */
	List<OrderStatus> queryOrderList(QueryListVo queryListVo,Page page);
	
	
	/**
	 * 获取更新结果
	 * @param v
	 * @return
	 */
	UpdateResultVo updateResultVo(VersionUpdateVo v);
	
	/**
	 * 获取需要更新的配置
	 * @param terminalVo
	 * @return
	 */
	List<UpdateConfigVo> quUpdateConfig(TerminalVo terminalVo);
	
	/**
	 * 更新终端设备状态vo
	 * @param configResultVo
	 */
	UpdateConfigResultVo updateConfigStatus(UpdateConfigResultVo configResultVo);
	
	/**
	 * 根据终端数据库保存状态
	 * @param restartVo
	 */
	RestartVo updateTerminalStatus(TerminalVo restartVo);
	
	/**
	 * 轮询检测
	 * @param baseTerminal
	 * @return
	 */
	AuthenOut pkgPoolingCheck(BaseTerminal baseTerminal);
	
	
	/**
	 * 揽收结果通知
	 * @return
	 */
	AuthenOut updateOrderInfoForStatus(TerResult terResult);
}
