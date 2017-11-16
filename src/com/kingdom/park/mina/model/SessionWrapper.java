package com.kingdom.park.mina.model;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.kingdom.park.mina.client.ParkClientHandler;
import com.kingdom.park.mina.client.ParkClientSupport;


/**
 * Session的包装类
 * @author YaoZhq
 */
public class SessionWrapper {
	private  IoSession session;

	private  long sessionId;
	/** IP地址 **/
	private  String ip;
	/** 端口 **/
	private  int port;
	/** 连接 **/
	private  NioSocketConnector connector;
	/** handler对象 **/
	private  ParkClientHandler handler;
	/** 客户端对象 **/
	private  ParkClientSupport client;

	/**
	 * 停车场授权码
	 */
	private String licenseKey;
	/**
	 * 重连次数
	 */
	private int reconnectsum=0;

	public SessionWrapper() {}

//    private static  final SessionWrapper single = new SessionWrapper();
//    //静态工厂方法
//    public static SessionWrapper getInstance() {
//        return single;
//    }


	private void initAddress() {
		String[] ads = getAddress();
		if (ads.length == 2) {
			this.ip = ads[0].trim();
			this.port = Integer.parseInt(ads[1].trim());
		}
	}

	public String[] getAddress() {
		String address = session.getRemoteAddress() == null ? ""
				: session.getRemoteAddress().toString();
		address = address.replaceAll("/", ""); //$NON-NLS-1$//$NON-NLS-2$
		return address.split(":");
	}


	public IoSession getSession() {
		return session;
	}


	public void setSession(IoSession session) {
		this.session = session;
	}


	public long getSessionId() {
		return sessionId;
	}


	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public NioSocketConnector getConnector() {
		return connector;
	}


	public void setConnector(NioSocketConnector connector) {
		this.connector = connector;
	}


	public ParkClientHandler getHandler() {
		return handler;
	}


	public void setHandler(ParkClientHandler handler) {
		this.handler = handler;
	}


	public ParkClientSupport getClient() {
		return client;
	}


	public void setClient(ParkClientSupport client) {
		this.client = client;
	}


	public String getLicenseKey() {
		return licenseKey;
	}


	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public int getReconnectsum() {
		return reconnectsum;
	}

	public void setReconnectsum(int reconnectsum) {
		this.reconnectsum = reconnectsum;
	}



}
