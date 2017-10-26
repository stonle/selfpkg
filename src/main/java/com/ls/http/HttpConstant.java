/**   
* 
*/
package com.ls.http;

/** 
* @ClassName: HttpConstant 
* @Description: 终端请求返回常量标识符
* @author huanglei
* @date 2017年9月9日 上午9:48:38 
* @version V1.0    
*/
public class HttpConstant {
	/**
	 * 视频服务地址
	 */
	public static final String VIE_PRE_URL = "http://192.168.0.76/thirdAccess/preview?previewType=2&resInfo.camerCode=";
	/**
	 * 加密固定字符串
	 */
	public static final String SALT = "b9def862ae52553aee456f33328e4017";

    /****--------错误码-----------***/
	
	public static final int SUCCESS_CODE = 0;
	
	public static final String SUCCESS_MSG = "success";
	
	public static final int DATA_DEFECT = 30000;
	
	public static final String DATA_DEFECT_MSG = "数据不全";
	
	public static final int LOGIN_FAIL = 30001;
	
	public static final String LOGIN_FAIL_MSG = "登录验证失败";
	
	public static final int AUTH_OUTTIME = 30002;
	
	public static final String  AUTH_OUTTIME_MSG = "身份验证超时";
	
	public static final int DATA_ERROR = 30003;
	
	public static final String  DATA_ERROR_MSG = "数据异常";
	
	public static final int CARD_NOT_LIKE = 30005;
	
	public static final String CARD_NOT_LIKE_MSG = "身份证号与注册的身份证账号不一致";
	
	public static final int GD_ERROR = 30006;
	
	public static final String GD_ERROR_MSG = "物品存在异常";
	
	public static final int CHARING_ERROR = 30009;
	
	public static final String CHARING_ERROR_MSG = "计费失败";
	
	public static final int CKECK_PKG_WEIHT = 30008;
	
	public static final String CKECK_PKG_WEIHT_MSG = "物品体积、重量发生较大改变";
	
	public static final int GET_PAY_CODE = 30011;
	
	public static final String GET_PAY_CODE_MSGT = "二维码获取失败";
	
	public static final int SIGN_AUTH_ERROR = 30014;
	
	public static final String SIGN_AUTH_ERROR_MSG = "sign数据验证失败";
	
	public static final int PANYMENT_ERROR = 30015;
	
	public static final String PANYMENT_ERROR_MSG = "支付失败";
	
	
	public static final int PAYMENT_EXCEPTION_CODE = 30016;
	
	public static final String PAYMENT_EXCEPTION_MSG = "支付异常请联系管理员";
	
	public static final int SERVICE_ERROR = 32000;
	public static final String SERVICE_ERROR_MSG ="后台服务错误"; 
	
	
	public static final int PAYMENT_AMOUNT_ERROR = 32001;
	public static final String PAYMENT_AMOUNT_MSG ="付款金额错误"; 
	
	
	public static final int TER_NOT_EXIST = 32002;
	public static final String TER_NOT_EXIST_MSG ="该终端没有上线";
	
	
	public static final int ALREAD_PAID_CODE = 32003;
	public static final String ALREAD_PAID_CODE_MSG = "已支付";
	
	/****----------请求码------------***/
	
	public static final int REQ_CHECK_HERATBEAT = 10000;
	
	public static final int REQ_LOGIN = 10001;
	
	public static final int REQ_AUTH = 10002;
	
	public static final int REQ_PKG_CHEKC = 10003;
	
	public static final int REQ_PKG_WEIGH = 10004;
	
	public static final int REQ_PANYMENT = 10005;
	
	public static final int REQ_CKECK_WEIGH = 10006;
	
	public static final int REQ_PAY_CODE = 10007;
	//揽收结果
	public static final int REQ_RESULT_CODE = 10008;
	
	public static final int REQ_GET_FIRST_VOLUME = 10011;
	
	public static final int REQ_QUERY_LIST = 10009;
	//程序更新
	public static final int REQ_PRO_UPDATE= 10010;
	//跟新配置字段
    public static final int REQ_UPDATE_CONFIG_FIELD = 10012;
    //更新配置结果
    public static final int REQ_UPDATE_RESULT = 10013;
    //收到重启，准备重启
    public static final int REQ_RESTART = 10014;
    //轮询（验视结果） 终端2分钟等待时间 ,每10秒轮询一次
    public static final int REQ_POLLING_CHECK = 10015;
	/****----------服务器返回码------------***/
	public static final int RSP_CHECK_HERATBEAT = 50000;
	
	public static final int RSP_LOGIN = 50001;
	
	public static final int RSP_AUTH = 50002;
	
	public static final int RSP_PKG_CHEKC = 50003;
	
	public static final int RSP_PKG_WEIGH = 50004;
	
	public static final int RSP_PANYMENT= 50005;
	
	public static final int RSP_CKECK_WEIGH= 50006;
	
	public static final int RSP_PAY_CODE= 50007;
	
    public static final int RSP_RESULT_CODE = 50008;
    
	public static final int RSP_GET_FIRST_VOLUME = 50011;
	
	public static final int RSP_QUERY_LIST = 50009;
	
	//程序更新返回码
	public static final int RSP_PRO_UPDATE = 50010;
	
	//更新字段返回码
	public static final int RSP_UPDATE_CONFIG_FIELD = 50012;
	//更新配置结果返回
	public static final int RSP_UPDATE_RESULT = 50013;
	//收到重启，准备重启返回
	public static final int RSP_RESTART = 50014;
	
	//包裹检查轮询返回
	public static final int RSP_POLLING_CHECK = 50015;
}
