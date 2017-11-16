package com.kingdom.park.mina.util;

import org.apache.log4j.Logger;

public class LoggerUtil {

	@SuppressWarnings("rawtypes")
	public static void logger(Class clazz,String info){
		Logger logger = Logger.getLogger(clazz);
		//logger.info("[" + TimeUtils.formatDateTime(TimeUtils.now()) + "]"+clazz+":"+info);
		logger.info(info);
	}
	@SuppressWarnings("rawtypes")
	public static void error(Class clazz,Exception e,String info){
		Logger logger = Logger.getLogger(clazz);
		//logger.info("[" + TimeUtils.formatDateTime(TimeUtils.now()) + "]"+clazz+":"+info);
		logger.error(info, e);
	}
}
