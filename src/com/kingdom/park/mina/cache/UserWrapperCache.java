package com.kingdom.park.mina.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.kingdom.park.mina.model.SessionWrapper;
import com.kingdom.park.mina.util.Assert;
import com.kingdom.park.mina.util.LoggerUtil;

/**
* @author CQling
* @version 2017年8月28日
* TODO
*/
public final class UserWrapperCache {


	/**
	 * 缓存sessionId或licenseKey和Status的键值对
	 */
	private Map<String, SessionWrapper> statusMap = new HashMap<String, SessionWrapper>();

	private static UserWrapperCache instance = new UserWrapperCache();

	private UserWrapperCache() {
	}

	public static UserWrapperCache getInstance() {
		return instance;
	}

	/**
	 * 添加session至缓存中
	 * @param user
	 * @return
	 */
	public  boolean updateSessionWrapperByKey(SessionWrapper user){
		boolean result=false;
		if(user!=null&&user.getSession()!=null){
			//判断原来缓存中是否存在，若存在，移除
			if(statusMap.containsKey(user.getLicenseKey())){
				String oldKey = user.getLicenseKey();
				long sesionId = user.getSessionId();
				removeSessionWrapper(oldKey);
				LoggerUtil.logger(UserWrapperCache.class, "更新Wrapper缓存,key:"+oldKey
						+"---sessionId:"+sesionId);
			}
			statusMap.put(String.valueOf(user.getSessionId()),user );
			statusMap.put(user.getLicenseKey(), user);
			result=true;
		}
		return result;
	}

	/**
	 * 添加session至缓存中
	 * @param user
	 * @return
	 */
	public  boolean addSessionWrapper(SessionWrapper user){
		boolean result=false;
		if(user!=null&&user.getSession()!=null){
			//判断原来缓存中是否存在，若存在，移除
			if(statusMap.containsKey(user.getLicenseKey())){
				removeSessionWrapper(user.getLicenseKey());
			}
			statusMap.put(String.valueOf(user.getSessionId()), user);
			statusMap.put(user.getLicenseKey(), user);
			result=true;
		}
		return result;
	}

	/**
	 * 查找缓存中是否存在该licenseKey
	 * @param licenseKey
	 * @return
	 */
	public boolean findSessionWrapper(String licenseKey){
		boolean result=statusMap.containsKey(licenseKey);
		return result;
	}

	/**
	 * 移除状态缓存
	 * @param licenseKey
	 */
	public void removeSessionWrapper(String licenseKey) {
		SessionWrapper temp = getSessionWrapperBylicenseKey(licenseKey);
		if (temp!=null) {
			statusMap.remove(String.valueOf(temp.getSessionId()));
			statusMap.remove(licenseKey);
		}
	}

	/**
	 * 根据session获取缓存中的UserSessionWrapper
	 * @param session
	 * @return
	 */
	public SessionWrapper getSessionWrapperBySession(IoSession session){
		try {
			if(session==null||session.getId()==0){
				return null;
			}
			SessionWrapper status=statusMap.get(String.valueOf(session.getId()));
			if(status==null||status.getSession()==null){
				return null;
			}
			if(status!=null&&status.getLicenseKey()!=null){
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.error(this.getClass(),e, e.getMessage());
		}
		return null;
	}

	/**
	 * 根据licenseKey获取缓存中的UserSessionWrapper
	 * @param licenseKey
	 * @return
	 */
	public SessionWrapper getSessionWrapperBylicenseKey(String licenseKey){
		try {
			if(Assert.isNull(licenseKey)){
				return null;
			}
			SessionWrapper user=statusMap.get(licenseKey);
			if(user!=null&&user.getClient()!=null){
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.error(UserWrapperCache.class, e,e.getMessage());
		}
		return null;
	}



}
