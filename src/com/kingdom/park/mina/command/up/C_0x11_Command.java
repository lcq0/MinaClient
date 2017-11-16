package com.kingdom.park.mina.command.up;

import org.apache.mina.core.session.IoSession;

import com.kingdom.park.mina.client.CommandHelper;
import com.kingdom.park.mina.command.CommandImpl;
import com.kingdom.park.mina.model.MessageBag;
import com.kingdom.park.mina.util.IConstants;
import com.kingdom.park.mina.util.LoggerUtil;

/**
 * 心跳上传
 * @author YaoZhq
 * 第一步：校验session是否有效
 * 第二步：发送
 *
 */
public class C_0x11_Command extends CommandImpl {

	private static final long serialVersionUID = 4217540424946422657L;


	@Override
	public Object execute(IoSession session, MessageBag message,Object checkLicense) {
		if (message instanceof MessageBag == false) {
			return null;
		}
		final MessageBag bag = (MessageBag) message;
		if (getUCmd().equals(bag.getBagCode())) {
			if(session==null||!session.isConnected()){
				return null;
			}
			try {
				LoggerUtil.logger(C_0x11_Command.class, "心跳上行sessionid"+session.getId());
				CommandHelper.sendCommand(session, bag.getBagCode(),message.getLicenseKey(), bag.getTextContent());

				//打印
		    	String	msg = bag.getBagCode() + IConstants.SPLITCHAR +bag.getLicenseKey()+IConstants.SPLITCHAR + bag.getTextContent() + "\n";
		    	bag.setStrData("发:" + msg);
		    	LoggerUtil.logger(this.getClass(), bag.getStrData());

			} catch (Exception e) {
				e.printStackTrace();
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
