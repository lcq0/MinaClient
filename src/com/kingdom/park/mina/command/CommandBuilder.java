package com.kingdom.park.mina.command;

import java.util.Date;
import com.kingdom.park.mina.codec.Coding;
import com.kingdom.park.mina.model.MessageBag;
import com.kingdom.park.mina.util.IConstants;
import com.kingdom.park.mina.util.TimeUtils;


/**
 * 创建各类命令的工具
 * @author YaoZhq
 */
public class CommandBuilder implements IConstants {

	/**
	 * 传建心跳请求
	 */
	public static MessageBag createHeartBeats(String LICENSE_KEY){
		try{
			String carportNum="0";
			String parkingcount="0";
			String dateStr=TimeUtils.formatDateTimeYYYYMMDDHHmmss(new Date());
			//创建MB
			MessageBag mb=new MessageBag();
			mb.setBagCode(Coding.toHexStringSingleByte(IConstants.U_0x11));
			mb.setLicenseKey(LICENSE_KEY);
			String data="{\"version\": \""+IConstants.ProtocolVersion+"\",\"uptime\": \""+dateStr+"\",\"freecount\": \""+carportNum+"\",\"parkingcount\": \""+parkingcount+"\",\"licensekey\":\""+LICENSE_KEY+"\"}";
			mb.setTextContent(data);
			mb.setBagLength(data.getBytes().length+47);
			mb.setTextLength(data.getBytes().length);

			return mb;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
