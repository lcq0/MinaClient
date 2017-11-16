/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package com.kingdom.park.mina.client;

import java.net.SocketAddress;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.kingdom.park.mina.cache.UserWrapperCache;
import com.kingdom.park.mina.model.SessionWrapper;
import com.kingdom.park.mina.model.TerminationWrapper;
import com.kingdom.park.mina.util.LoggerUtil;
import com.kingdom.park.mina.util.TimeUtils;
/**
 * A simple chat client for a given user.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class ParkClientSupport {
	public static final Logger logger = Logger.getLogger(ParkClientSupport.class);
	private final IoHandler handler;

	//private final String name;

	public IoSession session;

	public ParkClientSupport(String name, IoHandler handler) {
		if (name == null) {
			throw new IllegalArgumentException("Name can not be null");
		}
		//this.name = name;
		this.handler = handler;
	}

	public boolean connect(NioSocketConnector connector, SocketAddress address,
			boolean useSsl) {
		if (session != null && session.isConnected()) {
			throw new IllegalStateException(
					"Already connected. Disconnect first.");
		}

		try {
			IoFilter LOGGING_FILTER = new LoggingFilter();

			IoFilter CODEC_FILTER = new ProtocolCodecFilter(new MessageCodecFactory());

			connector.getFilterChain().addLast("mdc", new MdcInjectionFilter());
			connector.getFilterChain().addLast("codec", CODEC_FILTER);
			connector.getFilterChain().addLast("logger", LOGGING_FILTER);
			//去除MINA框架心跳机制，已自定义心跳机制
//			connector.getFilterChain().addLast(PdaKeepAliveFilter.FILTER_NAME, new PdaKeepAliveFilter(5, 20));

			final SocketAddress ad=address;
			//添加断线重连机制
	        // 添加重连监听
	        connector.addListener(new ParkIOListener() {
	            @Override
	            public void sessionDestroyed(IoSession arg0) throws Exception {
	            	for (;;) {
	            		SessionWrapper sessionWrapper=UserWrapperCache.getInstance().getSessionWrapperBySession(arg0);
		            	if (sessionWrapper==null) {
		            		LoggerUtil.logger(this.getClass(), "找不到对应的缓存信息sessionId："+arg0.getId());
		    				break;
		    			}
		            	 int temp = sessionWrapper.getReconnectsum()+1;
	                    try {
	                        Thread.sleep(5000);
	                        if(sessionWrapper.getConnector()==null){
	                        	LoggerUtil.logger(ParkClientSupport.class,"sessionWrapper.connector为空"+TimeUtils.formatDate(new Date()));
	                        	continue;
	                        }
	                        String licenseKey = sessionWrapper.getLicenseKey();
//	                        if (temp>ParkCache.reconnectcount) {//重连超过N次，直接断开
//	                        	UserWrapperCache.getInstance().removeSessionWrapper(licenseKey);
//	                        	parkLogDBService.updateParkStatus(6, licenseKey);
//	                        	//加入待停止定时器信息
//	                        	TerminationWrapper tempWrapper = new TerminationWrapper();
//	                        	tempWrapper.setLicenseKey(licenseKey);
//	                        	tempWrapper.setTimercount(0);
//	                        	break;
//							}
	                        ConnectFuture future = sessionWrapper.getConnector().connect(ad);
	                        future.awaitUninterruptibly();// 等待连接创建成功
	                        session = future.getSession();// 获取会话
	                        sessionWrapper.setSession(session);
	                        sessionWrapper.setSessionId(session.getId());
	                        if (session.isConnected()) {
	                        	LoggerUtil.logger(ParkClientSupport.class,"断线重连[" + session.getRemoteAddress() + "]成功:"+session.getId()+"\n重连次数："+temp);
	                        	sessionWrapper.setReconnectsum(temp);
	                        	UserWrapperCache.getInstance().updateSessionWrapperByKey(sessionWrapper);
	                            break;
	                        }else {
	                        	LoggerUtil.logger(ParkClientSupport.class,"断线重连[" + session.getRemoteAddress() + "]失败:"+session.getId()+"\n重连次数："+temp);
	                        	UserWrapperCache.getInstance().getSessionWrapperBylicenseKey(licenseKey).setReconnectsum(temp);
	                        }
	                    } catch (Exception ex) {
	                    	System.out.println("重连服务器登录失败,5秒再连接一次:" + ex.getMessage());
	                        LoggerUtil.error(ParkClientSupport.class, ex, "重连服务器登录失败,5秒再连接一次:");
	                        /*//失败则重新实例化
	                        ParkClient client = new ParkClient();
	                        client.initClient();*/
	                    }
	                }
	            }
	        });

			connector.getSessionConfig().setKeepAlive(false);

			connector.setHandler(handler);
			ConnectFuture future1 = connector.connect(address);
			future1.awaitUninterruptibly();
			if (!future1.isConnected()) {
				return false;
			}
			session = future1.getSession();
			//login();

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void login(String loginName, String password) {
//		sendCommand(IConstants.U_LOGINCMD, loginName + IConstants.SPLITCHAR + password);
	}

	public void broadcast(String message) {
		session.write("广播消息： " + message);
	}

	public void sendCommand(String bagcode,String  license, String message) {
		CommandHelper.sendCommand(session, bagcode,license, message);
	}


	public void sendCommand(String message) {
		//CommandHelper.sendSegComand(session, message);
	}

	public void quit(String loginName) {
		if (session != null) {
			if (session.isConnected()) {
//				sendCommand(IConstants.U_LOGOUTCMD, DateFormat.getDateTimeInstance().format(new Date()) + IConstants.SPLITCHAR + loginName);
				//session.write("QUIT");
				//sendCommand(session, "");
				// Wait until the chat ends.
				//session.getCloseFuture().awaitUninterruptibly();
			}
			session.close(true);
		}
	}
}
