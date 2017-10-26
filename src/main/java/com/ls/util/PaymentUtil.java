/**   
* 
*/
package com.ls.util;

/** 
* @ClassName: PaymentUtil 
* @Description:计费工具类
* @author huanglei
* @date 2017年9月6日 下午4:36:02 
* @version V1.0    
*/
public class PaymentUtil {
    
	//
	private static final int START_WEIGHT = 1;
	//
	private static final int STEP_WEIGHT = 1;
	//
	private static final int PRICE_START_WEIGHT = 10;
	//
	private static final int PRICA_INCR_WEIGHT = 5;
	//
	private static final int START_VOLUME = 1;
	//
	private static final int STEP_VOLUME = 1;
	//
	private static final int PRICE_START_VOLUME = 20;
	//
	private static final int PRICA_INCR_VOLUME = 5;
	
	public static double  getManyByWeight(double weight){
		weight = Math.floor(weight/1000);
		if(weight<=START_WEIGHT){
			return PRICE_START_WEIGHT;
		}else{
		  return (weight-START_WEIGHT)*PRICA_INCR_WEIGHT/STEP_WEIGHT + PRICE_START_WEIGHT;
		}
	}
	
	public static double  getManyByVolume(double volume){
		volume = Math.floor(volume/(1000*1000*1000));
		if(volume<=START_VOLUME){
			return PRICE_START_VOLUME;
		}else{
		  return (volume-START_VOLUME)*PRICA_INCR_VOLUME/STEP_VOLUME + PRICE_START_VOLUME;
		}
	}
}
