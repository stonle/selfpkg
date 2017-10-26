package com.ls.wx;

public enum WXEnum {
	APPID("appid"),
	MchId("mch_id"),
	NONCE_STR("nonce_str"),
	BODY("body"),
	OUT_TRADE_NO("out_trade_no"),
	TOTAL_FEE("total_fee"),
	SPBILL_CREATE_IP("spbill_create_ip"),
	NOTIFY_URL("notify_url"),
	TRADE_TYPE("trade_type"),
	SIGN("sign"),
	
	//二维码链接
	CODE_URL("code_url"),
	
	//请求方式
	POST("POST"),
	GET("GET"),
	//字符编码
	UTF8("UTF-8"),
	//trade_type 的方式
	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
	JSAPI("JSAPI"),
	NATIVE("NATIVE"),
	APP("app");
	 // 成员变量  
    private String name; 
    private WXEnum(String name){
    	this.name = name;
    }
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    } 
}
