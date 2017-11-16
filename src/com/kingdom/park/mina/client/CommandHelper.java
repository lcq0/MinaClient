/**
 *
 */
package com.kingdom.park.mina.client;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import com.kingdom.park.mina.codec.CRC16CCITT;
import com.kingdom.park.mina.codec.Coding;
import com.kingdom.park.mina.util.IConstants;
import com.kingdom.park.mina.util.LoggerUtil;

/**
 * @author Jonny
 * @create 2011-10-12 下午03:42:02
 */
public class CommandHelper implements IConstants {
	static final int num = 0x88;

	public static void sendCommand(IoSession session, String bagcode,String license, String msg) {
		if (msg != null && msg.length() > 0) {
			IoBuffer bf = null;
			try {
				bf = createBuffer(bagcode,license,msg);
				LoggerUtil.logger(CommandHelper.class, bagcode+"发送的文本内容:" + msg +"\n"+bagcode+"发送的数据 " + bf.getHexDump());
				if (bf != null) {
					session.write(bf);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LoggerUtil.error(CommandHelper.class, e, "发送的文本报错");
			} finally {
				if (bf != null) {
					bf.free();
				}
			}
		}
	}

	public static IoBuffer createBuffer(String bagcode,String license,String msg){
		try {
			String msgUTF = new String(msg.getBytes(),"UTF-8");
			/**不带消息体的包长度:包头标识符2+指令编号1+包长4+授权码32+文本长度4+校验码2+报尾标识2=47**/
			IoBuffer io = IoBuffer.allocate(msgUTF.getBytes().length+47);
			//标识码，包头
			io.putShort((short)0x2424);
			byte[] idb = Coding.toByteArray(bagcode);
			//协议码，消息ID
			io.put(idb);
			//包长度
			io.putInt(msgUTF.getBytes().length+47);
			//授权码
			byte[] licenseBytes = license.getBytes();
			io.put(licenseBytes);
			//文本长度
			io.putInt(msgUTF.getBytes().length);

			//文本内容
			io.put(msgUTF.getBytes());

			//生成校验码
			byte[] codea = new byte[msgUTF.getBytes().length+43];
			byte[] array=io.array();
			System.arraycopy(array, 0, codea, 0, codea.length);
//			short vaCode = DataBagUtil.getCRCCodeByte(codea);
			int vaCode = CRC16CCITT.codeBytes(codea);
			io.putShort((short)vaCode);

			//包尾标识符
			io.putShort((short)0x0d0a);
			io.flip();
//			System.out.println("发送的数据包：" + io.getHexDump());

			return io;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}



}
