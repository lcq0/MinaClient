package com.kingdom.park.mina.command;

import java.io.Serializable;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

import com.kingdom.park.mina.client.ParkClient;
import com.kingdom.park.mina.model.MessageBag;


/**
 * 处理接收到的消息的命令接口
 * @author YaoZhq
 */
public interface ICommand extends Serializable {
	/**
	 * 获得上行Command编号
	 * @return String
	 */
	String getUCmd();
	
	/**
	 * 获得下行Command编号
	 * @return String
	 */
	String getLCmd();
	
	/**
	 * 处理接收到的消息
	 * @param session 消息发送者的Session
	 * @param message 消息
	 * @param key 区分调用入口
	 * @return
	 */
	Object execute(IoSession session, MessageBag message,Object key);
	/**
	 * 对消息进行解码，便于后面进一步处理
	 * @param message 消息
	 * @return
	 */
	Object decodeMessage(Object message);
	/**
	 * 发送信息
	 * @param session
	 * @param sb
	 */
	WriteFuture sendCommand(IoSession session,StringBuffer sb);
	
}
