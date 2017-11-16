/**
 * 
 */
package com.kingdom.park.mina.model;

/**
 * @author Jonny
 * @create 2011-9-30 下午02:47:07
 */
public class User {
	private String loginName;
	private String password;
	private String name;
	
	/**
	 * 
	 */
	public User() {
	}
	
	/**
	 * @param loginName
	 * @param password
	 * @param name
	 */
	public User(String loginName, String password, String name) {
		super();
		this.loginName = loginName;
		this.password = password;
		this.name = name;
	}
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
