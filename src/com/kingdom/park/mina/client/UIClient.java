/**
 *
 */
package com.kingdom.park.mina.client;

/**
 * @author Jonny
 * @create 2011-11-30 下午05:46:27
 */
public interface UIClient {

//	public abstract void broadcast();
//
//	public abstract void afterBroadcast(ProtocolModel pm, String message);

	public abstract void sendCommand(String protocol,String license, String command);

	public abstract void cleanUI();

	public abstract void copyMessage();

	public abstract void clearMessage();

	public abstract void dispose();

	public abstract void quit();

//	public abstract void changeProtocol();

	public abstract void loggedOut();

	public abstract void initClient(String serverField,String LICENSE_KEY,boolean firstTime);
}
