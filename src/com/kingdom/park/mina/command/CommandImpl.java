/**
 * 
 */
package com.kingdom.park.mina.command;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import com.kingdom.park.mina.model.MessageBag;
import com.kingdom.park.mina.util.IConstants;

/**
 * 协议抽象Command实现
 */
public abstract class CommandImpl extends AbstractCommand implements IConstants {
	private static final long serialVersionUID = -6876150100410773516L;
	
	public static final Logger logger = Logger.getLogger(CommandImpl.class);

	/**
	 * 下行的指令流水号
	 */
	private static short SERIALIZABLE = 0;
	
	@Override
	public Object decodeMessage(Object message) {
		return null;
	}
	
	@Override
	public Object execute(IoSession session, MessageBag message,Object key) {
		return null;
	}

	/**
	 * 获取流水号
	 * @return
	 */
	public static short getSerializableNumber() {
		SERIALIZABLE ++;
		//流水号如果超过最大值就从头开始
		if (SERIALIZABLE == Short.MAX_VALUE) {
			SERIALIZABLE = 0;
		}
		return SERIALIZABLE;
	}

	@Override
	public WriteFuture sendCommand(IoSession session, StringBuffer sb) {
		return null;
	}


	/**
	 * 获取命令字节
	 * @param code
	 * @return
	 */
	public static String getCodeString(byte code) {
		String s = Integer.toString(code,16);
		if (s.length() == 1) {
			s = "0" + s;
		}
		return s;
	}
	

	
}
