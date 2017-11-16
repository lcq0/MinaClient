package com.kingdom.park.mina.client;

import java.nio.IntBuffer;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import com.kingdom.park.mina.codec.CRC16CCITT;
import com.kingdom.park.mina.codec.Coding;
import com.kingdom.park.mina.model.MessageBag;
import com.kingdom.park.mina.util.DataBagUtil;
import com.kingdom.park.mina.util.IConstants;
import com.kingdom.park.mina.util.LoggerUtil;
public class ParkMessageDecoder implements MessageDecoder {

	private static final Logger logger=Logger.getLogger(ParkMessageDecoder.class);

	/**
	 * 总包协议校验
	 */
	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in1) {
		MessageDecoderResult result = MessageDecoderResult.NOT_OK;
		try {
			String txt = in1.getHexDump(in1.remaining());
			LoggerUtil.logger(this.getClass(), "所有数据1:::"+txt);
			byte[] array = new byte[in1.remaining()];
			System.arraycopy(in1.array(), 0, array, 0, array.length);

			if(!DataBagUtil.is2424(array)&&!DataBagUtil.is0D0A(array) && array.length <47) {
				LoggerUtil.error(this.getClass(), null, "数据包长度不对，继续接收1:::"+txt);
				return MessageDecoderResult.NEED_DATA ;
			}


			int numbagpi = DataBagUtil.freeLengBag(array);
			if(numbagpi == -1){
				LoggerUtil.error(this.getClass(), null, "数据包长度不对，继续接收2:::"+txt);
				DataBagUtil.prinltnBytes2HexString("free read:",array);
				return MessageDecoderResult.NEED_DATA ;
			}

			//分包
			int isNum = 0 ;
			for(byte sing : array ){
				if(sing == IConstants.d_0x24) isNum ++;
			}

			if(isNum > 3){
				List<byte[]> byteList = DataBagUtil.subpackage(array);
				for(byte[] singbyte :byteList){
					 result =   decodableSing(singbyte);
				}
			 }else{
				 byte[] arraySing = new byte[array.length];
				 System.arraycopy(array, 0, arraySing, 0, arraySing.length);
				 result =   decodableSing(arraySing);
			 }

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), e, "decodeable总包异常！");
		}
		return result;

	 }

	/**
	 * 单包校验协议
	 * @param session
	 * @param in
	 * @return
	 */
	public MessageDecoderResult decodableSing(byte[] array) {
		String head = "";//包头
		IoBuffer in = IoBuffer.wrap(array);
		try {
			String txt = in.getHexDump(in.remaining());
			// 47字节长度
			if (in.remaining() < 47) {
				LoggerUtil.error(this.getClass(), null, "数据包长度不对，继续接收:::3"+txt);
				return MessageDecoderResult.NEED_DATA;
			}
			// 包头
			head = String.valueOf((Short)in.getShort());
			//----------消息体属性------------
			//协议指令码
			byte code=in.get();

			//包长
			int bagLength=in.getInt();

			//授权码
			byte[] licenseByte=new byte[32];
			in.get(licenseByte);

			//文本长度
			int texLength=in.getInt();

			//文本内容
			if(texLength>0){
				int remainLen=in.remaining();
				if(texLength>(remainLen-3)){
					return MessageDecoderResult.NOT_OK;
				}
				byte[] textContent=new byte[texLength];
				in.get(textContent);
			}

			//校验码,从消息头开始，与后一个字节异或，只到校验码前一位止
			short crc=in.getShort();
			byte[] codea = new byte[texLength+43];
			System.arraycopy(array, 0, codea, 0, codea.length);

			int validateCode=CRC16CCITT.codeBytes(codea);
			if(crc!=validateCode&&validateCode!=Coding.getUnsignedShort(crc)){
				LoggerUtil.error(this.getClass(), null, "subbag..校验码不符合:"+validateCode+"|"+crc+" ->"+txt);
				return MessageDecoderResult.NOT_OK;
			}

			byte[] end=new byte[2];
			in.get(end);
			return MessageDecoderResult.OK;

		} catch (Exception e) {
			//System.out.println("数据错误:" + e.getMessage());
			LoggerUtil.error(this.getClass(), e, "decodeable单包异常！");
			return MessageDecoderResult.NOT_OK;
		}
	}


	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in1, ProtocolDecoderOutput out) throws Exception {
		MessageDecoderResult result = MessageDecoderResult.NOT_OK;
		int freeint = 0;

		byte[] array = new byte[in1.remaining()];
		System.arraycopy(in1.array(), 0, array, 0, array.length);

		LoggerUtil.logger(this.getClass(), "decode所有数据:::"+in1.getHexDump(in1.remaining()));

		//分包
		int isNum = 0 ;
		for(byte sing : array ){
			if(sing == IConstants.d_0x24) isNum ++;
		}

		if(isNum > 3){
			List<byte[]> byteList = DataBagUtil.subpackage(array);
			for(byte[] singbyte :byteList){
				 freeint += singbyte.length;
				 result =  decodeSing(singbyte,out);
			}
		 }else{
			 byte[] arraySing = new byte[array.length];
			 System.arraycopy(array, 0, arraySing, 0, arraySing.length);
			 freeint += arraySing.length;
			 result =  decodeSing(arraySing,out);
		 }

		//释放掉数据包
		int numbagpi = DataBagUtil.freeLengBag(array);
		if(numbagpi != -1){
			if(in1.limit() > 0) in1.position(0);
			byte[] free = new byte[numbagpi];
			in1.get(free);
			if(freeint != free.length){
				logger.info("===[解码器]=释放数据包：：freeint:"+freeint +" free.length:"+free.length +"\n"+DataBagUtil.Bytes2HexString(free)+"\n"+DataBagUtil.Bytes2HexString(array));
			}else{
				DataBagUtil.prinltnBytes2HexString("numbagpi: "+numbagpi+" free: ",free,array);
			}
			if (in1 != null) {
				in1.free();
			}
			return result;
		}else{
			if (in1 != null) {
				in1.free();
			}
			return MessageDecoderResult.NEED_DATA ;
		}
	}

	/**
	 * 单包解析
	 * @param out
	 * @return
	 * @throws Exception
	 */
	public MessageDecoderResult decodeSing(byte[] array,
			ProtocolDecoderOutput out) throws Exception {
		IntBuffer buffer= IntBuffer.allocate(8);
		IoBuffer in = IoBuffer.wrap(array);
		try {
			System.arraycopy(in.array(), 0, array, 0, array.length);
			String txt = Coding.byte2HexStr(array);
			logger.info("*完整的原始数据Hex码-单包：" + txt);
			LoggerUtil.logger(this.getClass(), "decodeSing所有数据:::"+Coding.byte2HexStr(array));

			MessageBag bag = new MessageBag();

			//包头
			Short head = in.getShort();
			if (head != 0x2424) {
				return MessageDecoderResult.NOT_OK;
			}
			bag.setBagHead(String.valueOf(head));
			//----------消息体属性------------
			//协议指令码
			byte code=in.get();
			bag.setBagCode(Coding.toHexStringSingleByte(code));

			//包长
			int bagLength=in.getInt();
			bag.setBagLength(bagLength);

			//授权码
			byte[] licenseByte=new byte[32];
			in.get(licenseByte);
			String license=new String(licenseByte,"utf-8");
			bag.setLicenseKey(license);


			//文本长度
			int texLength=in.getInt();
			bag.setTextLength(texLength);

			//文本内容
			if(texLength>0){
				byte[] textContent=new byte[texLength];
				in.get(textContent);
				bag.setTextContentBytes(textContent);
				bag.setTextContentBytesHex(Coding.byte2HexStr(textContent));
				String textContentStr=new String(textContent,"UTF-8");
				bag.setTextContent(textContentStr);
			}

			//校验码,从消息头开始，与后一个字节异或，只到校验码前一位止
			short crc=in.getShort();
			byte[] codea = new byte[texLength+43];
			System.arraycopy(array, 0, codea, 0, codea.length);
			int validateCode=CRC16CCITT.codeBytes(codea);
			if(crc!=validateCode&&validateCode!=Coding.getUnsignedShort(crc)){
				System.out.println("subbag..校验码不符合:"+validateCode+"|"+crc+" ->"+txt);
				LoggerUtil.error(this.getClass(), null, "subbag..校验码不符合:"+validateCode+"|"+crc+" ->"+txt);
				return MessageDecoderResult.NOT_OK;
			}
			bag.setCrc(Short.toString(crc));


			byte[] end=new byte[2];
			in.get(end);
			bag.setBagEnd(Coding.byte2HexStr(end));

			//重要，不能删除
			out.write(bag);

		} catch (Exception e) {
			LoggerUtil.error(this.getClass(), null, "解析分包异常.....");
			return MessageDecoderResult.NOT_OK;
		}
		if (in != null) 	in.free();
		return MessageDecoderResult.OK;
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {

	}


}
