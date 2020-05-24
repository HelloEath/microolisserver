package com.hello.adminservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期操作工具类
 */
public class DateUtil {
	/**
	 * 默认日期格式：yyyy/MM/dd
	 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy/MM/dd";

	/**
	 * 默认日期格式：yyyyMMdd
	 */
	public static final String DATE_PATTERN = "yyyyMMdd";
	
	/**
	 * 默认时间格式：HH:mm:ss
	 */
	public static final String TIME_PATTERN = "HH:mm:ss";
	
	/**
	 * 默认时间格式：HHmmss
	 */
	public static final String TIME_MISS_PATTERN = "HHmmssS";

	/**
	 * 默认时间格式：yyyy/MM/dd HH:mm:ss
	 */
	public static final String DEFAULT_DATETIME_PATTERN = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 默认字符串格式：yyyyMMddHHmmss
	 */
	public static final String DEFAULT_STRING_PATTERN = "yyyyMMddHHmmss";

	/**
	 * 默认字符串格式：yyyyMMddHHmmssSSS
	 */
	public static final String DEFAULT_STRING_PATTERN17 = "yyyyMMddHHmmssSSS";

	/**
	 * 需要的字符串格式：yyyy-MM-dd HH:mm:ss nnn
	 */
	public static final String DEFAULT_STRINGFORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 需要的字符串格式：yyyyMMdd
	 */
	public static final String DEFAULT_SHORT_DATE_PATTERN = "yyyyMMdd";

	/**
	 * 默认时间戳格式，到毫秒 yyyy-MM-dd HH:mm:ss SSS
	 */
	public static final String DEFAULT_MILLISECOND_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 1天折算成毫秒数
	 * 24 * 3600 * 1000 = 86400000
	 */
	public static final long MILLIS_A_DAY = 86400000;

	/**
	 * 1分钟折算成毫秒数
	 * 60 * 1000 = 60000
	 */
	public static final long MILLIS_A_MIN = 60000;
	
	/**
	 * 获取时间的毫秒数
	 * @param date 时间
	 * @return 毫秒数
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c.getTimeInMillis();
	}
	
	/**
	 * 获取今天时间，格式：{@link #DEFAULT_SHORT_DATE_PATTERN}
	 * @return
	 */
	public static String getToday(){
		return getToday(DEFAULT_SHORT_DATE_PATTERN);
	}
	
	/**
	 * 获取当前时间
	 * @param pattern 格式
	 * @return 时间的字符串
	 */
	public static String getToday(String pattern){
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	/**
	 * 获取当前时间，格式：{@link #DEFAULT_STRING_PATTERN}
	 * @return
	 */
	public static String getCurrFullDate() {
		return getToday(DEFAULT_STRING_PATTERN);
	}

	/**
	 * 获取当前时间，格式：{@link #DEFAULT_STRING_PATTERN17}
	 * @return
	 */
	public static String getCurrFullDateWithMillSec() {
		return getToday(DEFAULT_STRING_PATTERN17);
	}
	
	/**
	 * 获取当前时间，格式：{@link #TIME_PATTERN}
	 * @return
	 */
	public static String getCurrPartTime() {
		return getToday(TIME_PATTERN);
	}
	
	/**
	 * 获取当前时间，格式：{@link #TIME_MISS_PATTERN}
	 * @return
	 */
	public static String getCurrPartTimeMiss() {
		return getToday(TIME_MISS_PATTERN);
	}
	
	/**
	 * 转换Date为字符格式
	 * @param date 时间
	 * @param pattern 格式
	 * @return
	 */
	public static String format(Date date,String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	} 
	
	/**
	 * 转换Date为字符格式
	 * @param date 时间 {@link #DEFAULT_STRING_PATTERN}
	 * @return
	 */
	public static String format(Date date) {
		return format(date,DEFAULT_STRING_PATTERN);
	}

	/**
	 * <pre>
	 * 获取两时间相差多少天
	 * </pre>
	 * @param startday 开始时间
	 * @param endday 结束时间
	 * @return 天数
	 */
	public static int getDiffDays(Date startday,Date endday){        
        if(startday.after(endday)){
            Date cal=startday;
            startday=endday;
            endday=cal;
        }        
        long sl=startday.getTime();
        long el=endday.getTime();       
        long ei=el-sl;           
        return (int)(ei/(MILLIS_A_DAY));
    }
	
	/**
	 * 获取昨天时间,格式：{@link #DATE_PATTERN}
	 * @return
	 */
	public static String getYesterday(){
		return getDay(-1);
	}
	/**
	 * 获取相差size天的时间,格式：{@link #DATE_PATTERN}
	 * @param size 
	 * @return
	 */
	public static String getDay(int size){
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.DAY_OF_MONTH, size);
		return formatter.format(c.getTime());
	}
	
	/**
	 * 获取相差hour的时间
	 * @param hour
	 * @param format 为空时默认为 {@link#DEFAULT_STRING_PATTERN}
	 * @return
	 */
	public static String getHour(int hour,String format){
		format = (format == null || "".equals(format)) ? DEFAULT_STRING_PATTERN : format;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.HOUR_OF_DAY, hour);
		return formatter.format(c.getTime());
	}

	/**
	 * <pre>
	 * 获取两时间相差多少分钟
	 * </pre>
	 * @param startday 开始时间
	 * @param endday 结束时间
	 * @return 分钟
	 */
	public static int getDiffMins(Date startday,Date endday){     
        long sl=startday.getTime();
        long el=endday.getTime();       
        long ei=el-sl;           
        return (int)(ei/(MILLIS_A_MIN));
    }
	
	/**
	 * 获取当天是当周的周几，从星期日开始 1.....
	 * @param day 格式yyyyMMdd
	 * @return 周几的数字
	 */
	public static int getDayInWeek(){
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		Date parse;
		try {
			parse = sf.parse(DateUtil.getToday());
			cl.setTime(parse);
		} catch (ParseException e) {
		}
		return cl.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 转换日期类型格式，将yyyyMMdd格式转换为yyyy-MM-dd格式
	 * @param date 日期字符串
	 * @return yyyy-MM-dd
	 */
	public static String formatDate(String date){
		SimpleDateFormat sf  = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfdate  = new SimpleDateFormat("yyyyMMdd");
		String format = date;
		try {
			Date parse = sfdate.parse(date);
			format = sf.format(parse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return format;
	}
	
	/**
	 * 转换日期类型格式，将yyyy-MM-dd格式转换为yyyyMMdd格式
	 * @param date 日期字符串
	 * @return yyyyMMdd
	 */
	public static String formatDateToYYYYMMDD(String date){
		SimpleDateFormat sf  = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfdate  = new SimpleDateFormat("yyyyMMdd");
		String format = date;
		try {
			Date parse = sf.parse(date);
			format = sfdate.format(parse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return format;
	}
	
	/**
	 * 获取当前日期的自然周（周一开始）的开始日期,结束时间
	 * 
	 * @return list
	 */
	public static List<String> getLastWeekStartDay(){
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if(1 == dayWeek){
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);
		//cal.add(Calendar.WEEK_OF_MONTH,-1);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
		
		String beginDayOfWeek = formatter.format(cal.getTime());
		
		cal.add(Calendar.DATE, 6);
		String endDayOfWeek = formatter.format(cal.getTime());
		List<String> dayList = new ArrayList<String>();
		dayList.add(beginDayOfWeek);
		dayList.add(endDayOfWeek);
		return dayList;
	}
	
	/**
	 * 获取两个日期间有几个星期一到星期日
	 * @param dateSta,dateEnd,formatter
	 * @returns
	 */
	public static List<Integer> getWeekDays(String dateSta, String dateEnd,String formatter) {
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
		Date date_sta;
		Date date_end;
		int weekday[] = new int [7];
		List<Integer> list = new ArrayList<Integer>();
		try {
			date_sta = simpleDateFormat.parse(dateSta);
			date_end = simpleDateFormat.parse(dateEnd);
			int days = getDiffDays(date_sta,date_end)+1;
			cl.setTime(date_sta);
			int sta_week_day = cl.get(Calendar.DAY_OF_WEEK)-1;
			int weeks = (int) Math.floor(days / 7);
			int day = days % 7;
			weekday[0] = weekday[1] = weekday[2] = weekday[3] = weekday[4] = weekday[5] = weekday[6] = weeks;
			for (int i = 0; i < day; i++) {
				int n = (sta_week_day + i) % 7;
				weekday[n]++;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.add(weekday[1]);
		list.add(weekday[2]);
		list.add(weekday[3]);
		list.add(weekday[4]);
		list.add(weekday[5]);
		list.add(weekday[6]);
		list.add(weekday[0]);
		return list;
	};
	/**
	 * 获取当前时间，格式：{@link #DEFAULT_STRING_PATTERN}
	 * @return
	 */
	public static String getCurrFullDateTime() {
		return getToday(DEFAULT_DATETIME_PATTERN);
	}
	
	/**
	 * 获取某一天的前或者后几个月
	 * @param day
	 * @param size
	 * @return
	 */
	public static String getBeforeOrAfterMonth(String day,int size) throws Exception{
		Calendar cal = Calendar.getInstance();
		Date date = null;
		date = new SimpleDateFormat("yyyyMMdd").parse(day);
		cal.setTime(date);
		int m = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, m+size);
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}
	
	/**
	 * 获取某一天相差size天的时间,格式：{@link #DATE_PATTERN}
	 * @param day
	 * @param size 
	 * @return
	 */
	public static String getBetweenDaysTime(String day,int size) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
		Calendar c = Calendar.getInstance(); 
		Date date = null;
		date = new SimpleDateFormat("yyyyMMdd").parse(day);
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, size);
		return formatter.format(c.getTime());
	}
	
	/**
	 * 将毫秒数转换为所需时间格式
	 * @param Millis
	 * @param pattern
	 * @return
	 */
	public static String formatLongToDate(Long Millis, String pattern) {
		if( Millis != null ) {
			Date date = new Date(Millis);
			return ( pattern != null && pattern.length() > 0 ) ? 
					format(date, pattern) : 
						format(date);
		}
		return null;
	}
	
	/**
	 * 比较两个日期大小（忽略时分秒）
	 * <p>mainDate > compareDate ==> 1</p>
	 * <p>mainDate = compareDate ==> 0</p>
	 * <p>mainDate < compareDate ==> -1</p>
	 * @param mainDate 比较主体
	 * @param compareDate 参照体
	 * @return
	 */
	public static Integer dateCompare(Date mainDate, Date compareDate) {
		Calendar mainCal = Calendar.getInstance();
		mainCal.setTime(mainDate);
		Calendar compareCal = Calendar.getInstance();
		compareCal.setTime(compareDate);
		if( mainCal.get(Calendar.YEAR) > compareCal.get(Calendar.YEAR) ) {
			return 1;
		}else if( mainCal.get(Calendar.YEAR) < compareCal.get(Calendar.YEAR) ) {
			return -1;
		}else if( mainCal.get(Calendar.YEAR) == compareCal.get(Calendar.YEAR) ) {
			if( mainCal.get(Calendar.MONTH) > compareCal.get(Calendar.MONTH) ) {
				return 1;
			}else if( mainCal.get(Calendar.MONTH) < compareCal.get(Calendar.MONTH) ) {
				return -1;
			}else if( mainCal.get(Calendar.MONTH) == compareCal.get(Calendar.MONTH) ) {
				if( mainCal.get(Calendar.DATE) > compareCal.get(Calendar.DATE) ) {
					return 1;
				}else if( mainCal.get(Calendar.DATE) < compareCal.get(Calendar.DATE) ) {
					return -1;
				}else if( mainCal.get(Calendar.DATE) == compareCal.get(Calendar.DATE) ) {
					return 0;
				}
			}
		}
		return null;
	}
	
}