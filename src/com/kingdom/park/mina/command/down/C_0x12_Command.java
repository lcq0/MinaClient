package com.kingdom.park.mina.command.down;

import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.mina.core.session.IoSession;

import com.kingdom.park.mina.cache.UserWrapperCache;
import com.kingdom.park.mina.command.CommandImpl;
import com.kingdom.park.mina.model.MessageBag;
import com.kingdom.park.mina.model.SessionWrapper;
import com.kingdom.park.mina.util.IConstants;
import com.kingdom.park.mina.util.JSONUtil;
import com.kingdom.park.mina.util.LoggerUtil;

/**
 * 心跳响应
 * @author YaoZhq
 * 第一步：解析响应数据
 * 第二步：更新客户端在线状态
 *
 */
public class C_0x12_Command extends CommandImpl {

	private static final long serialVersionUID = 4217540424946422657L;


	/**
	 * 报文解析
	 */
	@Override
	public Object decodeMessage(Object message) {
		try {
			MessageBag bag = (MessageBag) message;
			String json=bag.getTextContent();
//			json=ParkCache.GBKtoUTF(json);
			JSONObject jo=JSONObject.fromObject(json);
			if(jo==null||jo.isEmpty()){
				return null;
			}
			HashMap<String,String> mm=JSONUtil.SimpleJsonToHashMap(jo);
			return mm;
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), e, "");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object execute(IoSession session, MessageBag message,Object checkLicense) {
		System.out.println("55555555555");
		if (message instanceof MessageBag == false) {
			return null;
		}
		final MessageBag bag = (MessageBag)message;
		String licenseKey = bag.getLicenseKey();
		SessionWrapper sessionWrapper=UserWrapperCache.getInstance().getSessionWrapperBylicenseKey(licenseKey);
		if (sessionWrapper==null) {
			LoggerUtil.logger(C_0x12_Command.class, "未找到Wrapper，sessionId:"+session.getId()
							+"Key："+licenseKey);
			sessionWrapper = new SessionWrapper();
//			return null;
		}
		if (getLCmd().equals(bag.getBagCode())) {
			if(session==null||!session.isConnected()){
				return null;
			}
			//session中查询parkcode
	        String parkcode="";
	        HashMap<String,String> mm=(HashMap<String,String>)decodeMessage(bag);
	        String data=mm.get("data")==null?null:mm.get("data").toString();
	        if(data!=null&&!"".equals(data)){
	        	JSONArray ja=JSONArray.fromObject(data);
	        	if(ja!=null&&ja.size()>0){
	        		JSONObject jo=(JSONObject)ja.get(0);
	        		if(jo!=null&&jo.containsKey("parkcode")){
		        		parkcode=jo.getString("parkcode");
		        	}
	        	}
	        }else{/*
	        	//手动重连
	        	if(sessionWrapper.connector!=null){
	        		try {
	                    Thread.sleep(5000);
	                    ConnectFuture future = sessionWrapper.connector.connect();
	                    future.awaitUninterruptibly();// 等待连接创建成功
	                    session = future.getSession();// 获取会话
	                    if (session.isConnected()) {
	                    	System.out.println("手动重连，断线重连[" + sessionWrapper.connector.getDefaultRemoteAddress().getHostName() + ":" + sessionWrapper.connector.getDefaultRemoteAddress().getPort() + "]成功");
//	                        logger.info("手动重连，断线重连[" + sessionWrapper.connector.getDefaultRemoteAddress().getHostName() + ":" + sessionWrapper.connector.getDefaultRemoteAddress().getPort() + "]成功");
	                        LoggerUtil.logger(C_0x12_Command.class,"手动重连，断线重连[" + sessionWrapper.connector.getDefaultRemoteAddress().getHostName() + ":" + sessionWrapper.connector.getDefaultRemoteAddress().getPort() + "]成功");
	                    }
	                } catch (Exception ex) {
	                	ex.printStackTrace();
	                	System.out.println("手动重连，重连服务器登录失败,5秒再连接一次:" + ex.getMessage());
	                	 LoggerUtil.logger(C_0x12_Command.class,"手动重连，重连服务器登录失败,5秒再连接一次:" + ex.getMessage());
	                }
	        	}
	        */}
	        String result=mm.get("result")==null?null:mm.get("result").toString();
	        if (IConstants.Result_Code_Y.equals(result)) {
	        	 //记录本机库
		        boolean flag=true;
		        if(flag){
					//打印登录成功
					String msg="收：接收到心跳响应！上线成功.......0";
			    	LoggerUtil.logger(this.getClass(), msg);
			        isOnline=true;

					System.out.println("=======ID=="+sessionWrapper.getSessionId());
		        }
			}else {
				String msg="收：接收到心跳响应！上线失败.......0";
		    	LoggerUtil.logger(this.getClass(), msg);
		        isOnline=false;

				System.out.println("=======ID=="+sessionWrapper.getSessionId());
			}

		}
		return null;
	}

	@Override
	public String getLCmd() {
		return getCodeString(L_0x12);
	}

	@Override
	public String getUCmd() {
		return getCodeString(U_0x11);
	}


	public static void main(String[] agrs){
	}


}
