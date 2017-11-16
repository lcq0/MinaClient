package com.kingdom.park.mina.model;
/**
* @author CQling
* @version 2017年9月7日
* TODO停止用户信息
*/
public class TerminationWrapper {

	private String licenseKey;

	/**
	 * 定时器个数
	 */
	private int timercount;

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public int getTimercount() {
		return timercount;
	}

	public void setTimercount(int timercount) {
		this.timercount = timercount;
	}


}
