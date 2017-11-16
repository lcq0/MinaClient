package com.kingdom.park.mina.model;

import java.io.Serializable;

//通信消息包
public class MessageBag implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/** 包头 **/
	protected String bagHead;

	/** 协议码，如0x21 **/
	protected String bagCode;
	
	/** 数据包长 **/
	protected int bagLength;
	
	/**许可证**/
	protected String licenseKey;
	
	/**文本长度**/
	protected int textLength;
	
	/**文本内容**/
	protected String textContent;
	
	/**文本内容字节**/
	protected byte[] textContentBytes;
	
	/**文本内容16进制字符**/
	protected String textContentBytesHex;
	
	/**校验码**/
	protected String crc;
	
	/** 包尾 **/
	protected String bagEnd;
	
	/**
	 * 展示的请求
	 */
	protected String StrData;

	public String getBagHead() {
		return bagHead;
	}

	public void setBagHead(String bagHead) {
		this.bagHead = bagHead;
	}

	public String getBagCode() {
		return bagCode;
	}

	public void setBagCode(String bagCode) {
		this.bagCode = bagCode;
	}

	public int getBagLength() {
		return bagLength;
	}

	public void setBagLength(int bagLength) {
		this.bagLength = bagLength;
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public int getTextLength() {
		return textLength;
	}

	public void setTextLength(int textLength) {
		this.textLength = textLength;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public byte[] getTextContentBytes() {
		return textContentBytes;
	}

	public void setTextContentBytes(byte[] textContentBytes) {
		this.textContentBytes = textContentBytes;
	}

	public String getTextContentBytesHex() {
		return textContentBytesHex;
	}

	public void setTextContentBytesHex(String textContentBytesHex) {
		this.textContentBytesHex = textContentBytesHex;
	}

	public String getCrc() {
		return crc;
	}

	public void setCrc(String crc) {
		this.crc = crc;
	}

	public String getBagEnd() {
		return bagEnd;
	}

	public void setBagEnd(String bagEnd) {
		this.bagEnd = bagEnd;
	}

	public String getStrData() {
		return StrData;
	}

	public void setStrData(String strData) {
		StrData = strData;
	}
	
	

}
