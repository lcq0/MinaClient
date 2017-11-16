package com.kingdom.park.mina.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间格式化工具类
 * @author 作者:yaozq
 * @version 创建时间：2016-9-19 下午04:39:34
 *
 */

public class TimeUtils {
	private static Calendar cal = Calendar.getInstance();

	private static SimpleDateFormat dateFormat = new SimpleDateFormat();

	private static Date tempDate = new Date();
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * 得到当前时间
	 *
	 * @return
	 */
	public static Timestamp now() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public static Timestamp today() {
		Timestamp t = now();
		getDate(t);
		return t;
	}

	/**
	 * 时间比较
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compare(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		return compare(c1, c2);

	}

	/**
	 * 日期比较
	 *
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static boolean compare(Calendar c1, Calendar c2) {
		if (c1 == null || c2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}

		return c1.getTimeInMillis() > c2.getTimeInMillis();

	}

	public static void convertCal(Date t, int f, int ci) {
		cal.setTime(t);
		cal.set(f, cal.get(f) + ci);
		t.setTime(cal.getTimeInMillis());
	}

	public static void setFiled(Date t, int f, int si) {
		cal.setTime(t);
		cal.set(f, si);
		t.setTime(cal.getTimeInMillis());
	}

	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		dateFormat.applyPattern("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	public static String formatDateTime(Date date) {
		if (date == null) {
			return "";
		}
		dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	public static String formatDateTimeYYYYMMDDHHmmss(Date date) {
		if (date == null) {
			return "";
		}
		dateFormat.applyPattern("yyyyMMddHHmmss");
		return dateFormat.format(date);
	}

	public static String formatDateTimeYYYYMMDD(Date date) {
		if (date == null) {
			return "";
		}
		dateFormat.applyPattern("yyyyMMdd");
		return dateFormat.format(date);
	}

	public static Date formatStrToDate(String str) {
		if (Assert.isNull(str)) {
			return null;
		}
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		} catch (ParseException e) {
		}
		return d;
	}


	public static String formatDate(Date date, String pattern) {
		dateFormat.applyPattern(pattern);
		return dateFormat.format(date);
	}

	public static String formatDate(long d) {
		tempDate.setTime(d);
		return formatDate(tempDate);
	}

	public static String formatDateTime(long t) {
		tempDate.setTime(t);
		return formatDateTime(tempDate);

	}
	public static String formatDate(long t, String pattern) {
		tempDate.setTime(t);
		return formatDate(tempDate, pattern);
	}

	public static Timestamp getTimeStamp(String time) {
		if (time == null || time == "")
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = sdf.parse(time);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}
	/**
	 * 得到一天的开始时间
	 *
	 * @param d
	 * @return
	 */
	public static Timestamp getDayStart(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return new Timestamp(c.getTimeInMillis());
	}

	public static Timestamp getDayEnd(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 0);
		return new Timestamp(c.getTimeInMillis());

	}

	public static Timestamp getYearStart(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_YEAR, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return new Timestamp(c.getTimeInMillis());

	}

	public static Timestamp getYearEnd(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 31);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 0);
		return new Timestamp(c.getTimeInMillis());

	}

	public static Timestamp getMOnStart(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return new Timestamp(c.getTimeInMillis());

	}

	public static Timestamp getMonEnd(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, 31);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 0);
		return new Timestamp(c.getTimeInMillis());

	}


	public static DateFormat getDateFormat() {
		return dateFormat;
	}

	/**
	 * 判断是否在指定的区间
	 *
	 * @param startTime
	 * @param endTime
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static boolean test(String startTime, String endTime, Date d)
			throws ParseException {
		String str = formatDate(getFilterDate(now()));
		Date d1 = null;
		Date d2 = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		d1 = dateFormat.parse(str + " " + startTime);
		d2 = dateFormat.parse(str + " " + endTime);
		if (d1 == null || d2 == null || d == null) {
			return false;
		}
		return d.getTime() >= d1.getTime() && d.getTime() <= d2.getTime();
	}

	public static Date parse(String date) {
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public static Timestamp parseTime(String str) {
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(str);
		} catch (ParseException e) {
		}
		return d != null ? new Timestamp(d.getTime()) : null;

	}

	/**
	 * 判断是否为同一天
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if ((date1 == null) || (date2 == null)) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * 根据cal1判断是否为同一天
	 *
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if ((cal1 == null) || (cal2 == null)) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return ((cal1.get(0) == cal2.get(0)) && (cal1.get(1) == cal2.get(1)) && (cal1
				.get(6) == cal2.get(6)));
	}

	public static boolean isSameInstant(Date date1, Date date2) {
		if ((date1 == null) || (date2 == null)) {
			throw new IllegalArgumentException("The date must not be null");
		}
		return (date1.getTime() == date2.getTime());
	}

	private static void getDate(Timestamp t) {
		setFiled(t, 11, 0);
		setFiled(t, 12, 0);
		setFiled(t, 13, 0);
		setFiled(t, 14, 0);
	}

	public static Date getFilterDate(String str) {
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		} catch (ParseException e) {
		}
		return getFilterDate(d != null ? d : new Date());
	}

	/**
	 * 格式化时间字符串
	 *
	 * @param str
	 * @return
	 */
	public static String format(String str) {
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		} catch (ParseException e) {
		}
		return formatDate(d != null ? d : new Date());
	}

	public static Date getFilterDate(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 在当前天与hms构成一个timestamp时间对象
	 * @param hms 时分秒
	 * @return
	 */
	public static Timestamp getNowTimeByHMS(String hms)
	{
		try {
		String ymd=new SimpleDateFormat("yyyy-MM-dd").format(now());

		ymd=ymd+" "+hms;
		Date dt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ymd);
		return new Timestamp(dt.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 在当前天加day天与hms构成一个timestamp时间对象
	 * @param day 要加的天数
	 * @param hms 时分秒
	 * @return
	 */
	public static Timestamp getNowTimeByHMSAddDay(int day,String hms)
	{
		try {
		String ymd=new SimpleDateFormat("yyyy-MM-dd").format(now());

		ymd=ymd+" "+hms;
		Date dt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ymd);
		dt.setDate(dt.getDate()+day);
		return new Timestamp(dt.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	* 取得当前日期是多少周
	*
	* @param date
	* @return
	*/
	public static int getWeekOfYear(Date date) {
	Calendar c = new GregorianCalendar();
	c.setFirstDayOfWeek(Calendar.MONDAY);
	c.setMinimalDaysInFirstWeek(7);
	c.setTime (date);

	return c.get(Calendar.WEEK_OF_YEAR);
	}


	/**
	* 得到某一年周的总数
	*
	* @param year
	* @return
	*/
	public static int getMaxWeekNumOfYear(int year) {
	Calendar c = new GregorianCalendar();
	c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

	return getWeekOfYear(c.getTime());
	}
	/**
	* 得到某年某周的第一天
	*
	* @param year
	* @param week
	* @return
	*/
	public static Date getFirstDayOfWeek(int year, int week) {
	Calendar c = new GregorianCalendar();
	c.set(Calendar.YEAR, year);
	c.set (Calendar.MONTH, Calendar.JANUARY);
	c.set(Calendar.DATE, 1);

	Calendar cal = (GregorianCalendar) c.clone();
	cal.add(Calendar.DATE, week * 7);

	return getFirstDayOfWeek(cal.getTime ());
	}

	/**
	* 得到某年某周的最后一天
	*
	* @param year
	* @param week
	* @return
	*/
	public static Date getLastDayOfWeek(int year, int week) {
	Calendar c = new GregorianCalendar();
	c.set(Calendar.YEAR, year);
	c.set(Calendar.MONTH, Calendar.JANUARY);
	c.set(Calendar.DATE, 1);

	Calendar cal = (GregorianCalendar) c.clone();
	cal.add(Calendar.DATE , week * 7);

	return getLastDayOfWeek(cal.getTime());
	}

	/**
	* 取得当前日期所在周的第一天
	*
	* @param date
	* @return
	*/
	public static Date getFirstDayOfWeek(Date date) {
	Calendar c = new GregorianCalendar();
	c.setFirstDayOfWeek(Calendar.MONDAY);
	c.setTime(date);
	c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
	return c.getTime ();
	}

	/**
	* 取得当前日期所在周的最后一天
	*
	* @param date
	* @return
	*/
	public static Date getLastDayOfWeek(Date date) {
	Calendar c = new GregorianCalendar();
	c.setFirstDayOfWeek(Calendar.MONDAY);
	c.setTime(date);
	c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
	return c.getTime();
	}


	/**
	 * 某年某月的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month){

	       Calendar lastDate = Calendar.getInstance();
	       lastDate.set(Calendar.YEAR, year);
	       lastDate.set(Calendar.MONTH,month-1);
	       lastDate.set(Calendar.DATE,1);//设为当前月的1号
	       return lastDate.getTime();

	    }

	/**
	 * 某年某月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month){
	  Calendar lastDate = Calendar.getInstance();
	  		lastDate.set(Calendar.YEAR, year);
	       lastDate.set(Calendar.DATE,1);//设为当前月的1号
	       lastDate.set(Calendar.MONTH,month);//设为下一个月
	       lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
	       return lastDate.getTime();

	    }


	/**
	 * 取得当前月份
	 * @return
	 */
	public static int getCurrentMonth()
	{
		Calendar rightNow = Calendar.getInstance();
		return rightNow.get(Calendar.MONTH)+1;
	}

	/**
	 * 取得当前季度
	 * @return
	 */
	public static int getCurrentJidu()
	{
		int month=getCurrentMonth();
		int jidu=0;
		if(month>=10)
			jidu=4;
		else if(month>=7)
			jidu= 3;
		else if(month>=4)
			jidu= 2;
		else if(month>=1)
			jidu= 1;
		return jidu;
	}

	/**
	 * 某年某季度的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfJiDU(int year, int jidu){
		int month=0;
		if(jidu==1)
		{
			month=1;

		}
		if(jidu==2)
		{
			month=4;

		}
		if(jidu==3)
		{
			month=7;

		}
		if(jidu==4)
		{
			month=10;

		}

		return getFirstDayOfMonth(year,month);

	    }
	/**
	 * 某年某季度的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfJiDU(int year, int jidu){
		int month=0;
		if(jidu==1)
		{
			month=3;

		}
		if(jidu==2)
		{
			month=6;

		}
		if(jidu==3)
		{
			month=9;

		}
		if(jidu==4)
		{
			month=12;

		}

		return getLastDayOfMonth(year,month);

	    }


	/**
	 * 取得当前是上半年还是下半年
	 * @return
	 */
	public static int getCurrentBanLian()
	{
		if(getCurrentMonth()>=7)
			return 2;
		else
			return 1;

	}



	/**
	 * 某年某半年的第一天
	 * @param year
	 * @param sx
	 * @return
	 */
	public static Date getFirstDayOfBanLian(int year, int sx){
		int month=0;
		if(sx==1)
		{
			month=1;

		}
		else
		{
			month=7;
		}

		return getFirstDayOfMonth(year,month);

	    }
	/**
	 * 某年某半年的最后一天
	 * @param year
	 * @param sx
	 * @return
	 */
	public static Date getLastDayOfBanLian(int year, int sx){
		int month=0;
		if(sx==1)
		{
			month=6;

		}
		if(sx==2)
		{
			month=12;

		}

		return getLastDayOfMonth(year,month);

	    }

	/**
	 * 取得当前年
	 * @return
	 */
	public static int getCurrentYear()
	{
		Calendar rightNow = Calendar.getInstance();
		return rightNow.get(Calendar.YEAR);
	}


	/**
	 * 将字符串转化为timesTamp
	 *
	 * */
	public static Timestamp getStrToTimesTamp(String timeStr){
		SimpleDateFormat  format = new SimpleDateFormat("yyyy");
		String  year = format.format(new Date());
		StringBuffer result  = new StringBuffer("");
		String dateArray [];
		timeStr.replace("AM", "");
    	timeStr.replace("am", "");
    	timeStr.replace("PM", "");
    	timeStr.replace("pm", "");
    	String strArray [] = timeStr.trim().split(" ");
    	if(timeStr.contains("-")){
    		dateArray =  strArray[0].trim().split("-");
    	}else{
    		dateArray =  strArray[0].trim().split("/");
    	}

    	//年
    	if(dateArray[0].length()==2){
    		System.out.println(year);
    		result.append(year.substring(0, 2));
    		result.append(dateArray[0]+"-");
    	}else{
    		result.append(dateArray[0]+"-");
    	}
    	if(dateArray[1].length()==1){
    		result.append("0"+dateArray[1]+"-");
    	}else{
    		result.append(dateArray[1]+"-");
    	}
    	if(dateArray[2].length()==1){
    		result.append("0"+dateArray[2]);
    	}else{
    		result.append(dateArray[2]);
    	}

    		if(strArray[strArray.length-1].contains(":")){
    			result.append(" "+strArray[strArray.length-1]);
    		}else{
    			result.append(" 00:00:00");
    		}

		return Timestamp.valueOf(result.toString());

	}
	/**
	 * 根据日期偏移量(dayOffset)取得给定日期（时间为"00:00:00"）
	 * 如dayOffset=-1，则返回昨天日期，dayOffset=1，则返回明天日期
	 *
	 * @param dayOffset
	 *            日期偏移量
	 * @return
	 */
	public static java.util.Date getDateByDayOffset(java.util.Date date,
			int dayOffset) {
		if (Assert.isNull(date)) {
			return date;
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, dayOffset);
			date = dateFormatter.parse(dateFormatter.format(cal.getTime()));
		} catch (ParseException ex) {
			LoggerUtil.error(TimeUtils.class, ex, ex.getMessage());
		}
		return date;
	}

	public static void main(String[] args) {
		System.out.println(formatDateTimeYYYYMMDD( getDateByDayOffset(new Date(), -1)));
	}
}
