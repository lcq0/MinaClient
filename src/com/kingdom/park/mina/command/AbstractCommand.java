package com.kingdom.park.mina.command;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import com.kingdom.park.mina.codec.CRC16CCITT;
import com.kingdom.park.mina.codec.Coding;
import com.kingdom.park.mina.model.MessageBag;
import com.kingdom.park.mina.util.IConstants;
import com.kingdom.park.mina.util.LoggerUtil;

/**
 * 命令处理抽象类
 * @author YaoZhq
 *
 */
public abstract class AbstractCommand implements ICommand, IConstants {
	private static final long serialVersionUID = 1L;

	public static final Logger logger = Logger.getLogger(AbstractCommand.class);

	public MessageBag bag;

	//是否在线(与服务端的连接)
	public static boolean isOnline=false;



	/**
	 * 下行指令
	 * @param session
	 * @param bag
	 * ————————————————————————————————————————————————————————————————————————————————————————
		  包头标识    | 协议指令码  |   包长    |   授权码     |  文本长度  |  文本内容    |  校验码      |  包尾标识
		————————————————————————————————————————————————————————————————————————————————————————
		  2 Byte  |  1 Byte   | 4 Byte |  32 Byte  |  4 Byte  |   N Byte  |   1 Byte |  2 Byte
		————————————————————————————————————————————————————————————————————————————————————————
		 0x240x24 |   ...     |  ...   |    ...    |    ...   |    ...    |    ...   |  0x0D0x0A
		————————————————————————————————————————————————————————————————————————————————————————
	 * @return
	 */
	public static WriteFuture sendCommand(IoSession session, MessageBag bag) {
		if (bag == null||bag.getTextContentBytes().length==0) {
			return null;
		}
		try{
			/**不带消息体的包长度:包头标识符2+指令编号1+包长4+授权码32+文本长度4+校验码2+报尾标识2=47**/
			IoBuffer io = IoBuffer.allocate(bag.getBagLength());
			//标识码，包头
			io.putShort(s_0x2424);
			byte[] idb = Coding.toByteArray(bag.getBagCode());
			//协议码，消息ID
			io.put(idb);
			//包长度
			io.putInt(bag.getBagLength());
			//授权码
			String license = bag.getLicenseKey();
			if(license==null||license.length()!=32){
				return null;
			}
			byte[] licenseBytes = license.getBytes();
			io.put(licenseBytes);
			//文本长度
			io.putInt(bag.getTextLength());

			//文本内容
			io.put(bag.getTextContentBytes());

//			//生成校验码
//			byte[] codea = new byte[bag.getBagLength() - 1];
//			byte[] array=io.array();
//			System.arraycopy(array, 0, codea, 0, codea.length);
//			byte vaCode = DataBagUtil.getCRCCodeByte(codea);
//			io.put(vaCode);

			//生成校验码
			byte[] codea = new byte[bag.getTextLength() +43];
			byte[] array=io.array();
			System.arraycopy(array, 0, codea, 0, codea.length);
//			short vaCode = DataBagUtil.getCRCCodeByte(codea);
			int vaCode = CRC16CCITT.codeBytes(codea);
			io.putShort((short)vaCode);
//
			//包尾标识符
			io.putShort(s_0x0d0a);

			io.flip();
			logger.info("===[数据下发]=session="+session.getId()+"=发送的数据包：："+io.getHexDump());

			WriteFuture wf = session.write(io);
			wf.addListener(new IoFutureListener<IoFuture>() {
				@Override
				public void operationComplete(IoFuture future) {
					if (future.isDone()) {
						logger.info("===[数据下发]=发送成功！=session=：："+future.getSession().getId());
					}
				}
			});
			return wf;
		}catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.error(AbstractCommand.class, e, "AbstractCommand出错");
		}
		return null;
	}




}
