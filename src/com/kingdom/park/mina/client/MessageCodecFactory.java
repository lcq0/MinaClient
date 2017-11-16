package com.kingdom.park.mina.client;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

public class MessageCodecFactory extends DemuxingProtocolCodecFactory {
	public MessageCodecFactory(){
		super.addMessageDecoder(ParkMessageDecoder.class);
		super.addMessageEncoder(Object.class ,ParkMessageEncoder.class);
	}
}
