package com.ls.wx;

/**
 * 微信支付工具类·
 * 
 * @author Administrator
 *
 */
public class WXConfigUtil {

	/**
	 * 微信应用支付appid 扫码支付appid
	 */
	public static final String APPID = "wx42f9aca75627bdd7";

	/**
	 * 商用号
	 */
	public static final String MCH_ID = "1488493102";

	/**
	 * 签名密匙
	 */
	public static final String API_KEY = "svMlFFTcrkuEylg7ltNvWBK8eDiN0GW2";// API密钥

	/**
	 * 签名的加密方式
	 */
	public static final String SIGN_TYPE = "MD5";// 签名加密方式
	/**
	 * 微信支付路径 https://api.mch.weixin.qq.com/pay/unifiedorder 扫码支付路径
	 * https://api.mch.weixin.qq.com/pay/unifiedorder
	 */
	public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
   
    /**
     * 支付接口回调通知请求
     */
	public static final String CALLBACK_REQ_METHOD="appWxNotify.do";
}
