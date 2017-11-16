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

import java.util.ArrayList;
import java.util.List;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.kingdom.park.mina.command.ICommand;
import com.kingdom.park.mina.command.down.C_0x12_Command;
import com.kingdom.park.mina.model.MessageBag;
import com.kingdom.park.mina.util.LoggerUtil;


/**
 * {@link IoHandler} implementation of the client side of the simple chat protocol.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class ParkClientHandler extends IoHandlerAdapter {

	private List<ICommand> commands = new ArrayList<ICommand>();

    public interface Callback {
        void connected();

        void loggedIn();

        void loggedOut();

        void disconnected();

        void messageReceived(IoSession session,Object message);

        void error(String message);
    }

    private final Callback callback;

    public ParkClientHandler(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void sessionOpened(IoSession session) {
    	try {
    		LoggerUtil.logger(ParkClientHandler.class, "sessionOpened"+(session==null?"session为空":"session_id："+session.getId()));
    		 callback.connected();
    	        ICommand C_0x12_Command=new C_0x12_Command();
    	        commands.add(C_0x12_Command);
		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), e, "打开session出错");
		}
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
    	callback.messageReceived(session,message);

    	if(message instanceof MessageBag){
    		if (session==null||!session.isConnected()) {
				LoggerUtil.logger(this.getClass(), "session为空id:"+session.getId());
			}
    		MessageBag msg=(MessageBag)message;
    		String re = msg.getTextContent();
    		String code = msg.getBagCode();
    		System.out.println(code+"========客户端接收数据===="+re);
    		LoggerUtil.logger(ParkClientHandler.class, code+"========客户端接收数据===="+re);
    		LoggerUtil.logger(this.getClass(), "客户端接收数据session_id:"+session.getId());
//    		sessionClosed(session);
    		if (session!=null&&session.isConnected()) {
    			if (commands==null||commands.isEmpty()||commands.size()<=0) {
    				LoggerUtil.logger(ParkClientSupport.class, "commands为空");
    				sessionOpened(session);
    			}
			}
        	for (ICommand command : commands) {
    			command.execute(session, msg,"");
    		}
    	}

    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
    	super.sessionClosed(session);
        callback.disconnected();
        commands.clear();

        LoggerUtil.logger(ParkClientHandler.class, "sessionClosed"+(session==null?"session为空":"session_id："+session.getId()));
    }


    @Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		super.exceptionCaught(session, cause);
		session.close(true);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
	}

}
