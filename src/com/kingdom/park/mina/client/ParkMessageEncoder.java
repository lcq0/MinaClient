/**
 * 
 */
package com.kingdom.park.mina.client;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

/**
 * @author Jonny
 * @create 2011-9-30 上午11:39:25
 */
public class ParkMessageEncoder implements MessageEncoder<Object> {

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.demux.MessageEncoder#encode(org.apache.mina.core.session.IoSession, java.lang.Object, org.apache.mina.filter.codec.ProtocolEncoderOutput)
	 */
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		System.out.println(message);
	}

}
