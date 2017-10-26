/**   
* 
*/
package com.ls.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ls.http.HeartBeat;

/** 
* @ClassName: Excle2EntityUtil 
* @Description: 
* @author huanglei
* @date 2017年9月9日 上午8:40:18 
* @version V1.0    
*/
public class ExcleEntityUtil<T> {
	private T entity;
    
    public ExcleEntityUtil(T entity){
        this.entity = entity;
    }
    
    public T doMapFormatEntity(LinkedHashMap<String,Object> hashMap) throws Exception{
    	for(Map.Entry<String, Object> entry : hashMap.entrySet()){
    		doInitEntity(entry.getKey(),entry.getValue());
    	}
    	return entity;
    }
    
    public void doInitEntity(String methodName , Object obj) 
        throws SecurityException, NoSuchMethodException, IllegalArgumentException, 
        IllegalAccessException, InvocationTargetException{
         
        //根据传入的属性名称构造属性的set方法名
        methodName = "set"+methodName;
        //System.out.println("Method Name --"+methodName);
        Method[] methods = entity.getClass().getMethods();
        for(Method method:methods){
            //如果方法同名则执行该方法（不能用于BO中有重载方法的情况）
            if(methodName.equalsIgnoreCase(method.getName())){
                method.invoke(entity, obj);
            }
        }
    }

}
