/**   
* 
*/
package com.ls.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RestController;

import com.ls.domain.PkgCheckInfo;
import com.ls.service.PkgCheckService;

import net.sf.json.JSONArray;

/**  
* @ClassName: WebSocketTest 
* @Description: 
* @author huanglei
* @date 2017年9月27日 下午5:45:09 
* @version V1.0    
*/
@RestController
@ServerEndpoint("/websocket/{userId}")
public class WebSocketController implements ApplicationContextAware {
	  
	  private static Logger logger = LogManager.getLogger(WebSocketController.class.getName());
	  
	  private static ApplicationContext APPLICATION_CONTEXT;
	  @Override
	  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		 APPLICATION_CONTEXT = applicationContext;
	   }
	  public static ApplicationContext getApplicationContext() {
	        return APPLICATION_CONTEXT;
	    }
	  
	  //存储连接
	  private static final Map<String,Object> sessionMap = new HashMap<String,Object>();
	  //定时任务
	  private Timer timer;
	  
	  @OnMessage
	  public void onMessage(@PathParam("userId")String userId,String message, Session session) 
	    throws IOException, InterruptedException {
		//修改数据type状态，type=3
		APPLICATION_CONTEXT.getBean(PkgCheckService.class).updatePkgCheckInfo(userId,message);
		session.getBasicRemote().sendText("ok");
		//遍历map发送消息
		if(sessionMap.size()>0){
			List<PkgCheckInfo> list = APPLICATION_CONTEXT.getBean(PkgCheckService.class).getPkgCheckInfos();
			String json = JSONArray.fromObject(list).toString();
			for(String key:sessionMap.keySet()){
				if(!session.equals(sessionMap.get(key))){
					Session ses = (Session) sessionMap.get(key);
					ses.getBasicRemote().sendText(json);	
				}
			}
		 }
	  }
	  
	  @OnOpen
	  public void onOpen(@PathParam("userId")String userId,Session session){
		sessionMap.put(userId, session);
		//獲取所有的查詢數據
		List<PkgCheckInfo> list = APPLICATION_CONTEXT.getBean(PkgCheckService.class).getPkgCheckInfos();
		String json = JSONArray.fromObject(list).toString();
		try {
			session.getBasicRemote().sendText(json);
		} catch (IOException e) {
			try {
				session.close();
			} catch (IOException e1) {
				logger.error("session Error: " + e1.toString(), e1); 
			}
		}
		timer = new Timer(true);
	    long delay = 0;
	    timer.schedule(new OrderTimeTask(session), delay, 10000);
	    System.out.println("Client connected");
	  }

	  @OnClose
	  public void onClose(@PathParam("userId")String userId,Session session) {
		sessionMap.remove(userId);
		timer.cancel();
	    System.out.println("Connection closed");
	  }
	  
	  
	  @OnError  
	  public void onError(Throwable t){  
		  logger.error("Chat Error: " + t.toString(), t);  
	    }  
	  class OrderTimeTask extends TimerTask{
		private Session session;
		public OrderTimeTask(Session se){
			session = se;
		}
		@Override
		public void run() {
			//獲取所有的查詢數據
			List<PkgCheckInfo> list = APPLICATION_CONTEXT.getBean(PkgCheckService.class).getPkgCheckInfos();
			String json = JSONArray.fromObject(list).toString();
			try {
					session.getBasicRemote().sendText(json);
			} catch (IOException e) {
				try {
					session.close();
				} catch (IOException e1) {
					logger.error("session Error: " + e1.toString(), e1);  
				}
				logger.error("Timer Run Error: " + e.toString(), e);  
			}
		 }
	  }
}
