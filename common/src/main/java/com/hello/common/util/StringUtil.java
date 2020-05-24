package com.hello.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * <p>
	 * The maximum size to which the padding constant(s) can expand.
	 * </p>
	 */
	private static final int PAD_LIMIT = 8192;

	/**
	 * <p>
	 * An array of <code>String</code>s used for padding.
	 * </p>
	 *
	 * <p>
	 * Used for efficient space padding. The length of each String expands as
	 * needed.
	 * </p>
	 */
	private static final String[] PADDING = new String[Character.MAX_VALUE];

	static {
		// space padding is most common, start with 64 chars
		PADDING[32] = "                                                                ";
	}

	/**
	 * <p>
	 * Returns padding using the specified delimiter repeated to a given length.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.padding(0, 'e')  = &quot;&quot;
	 * StringUtils.padding(3, 'e')  = &quot;eee&quot;
	 * StringUtils.padding(-2, 'e') = IndexOutOfBoundsException
	 * </pre>
	 *
	 * @param repeat
	 *            number of times to repeat delim
	 * @param padChar
	 *            character to repeat
	 * @return String with repeated character
	 * @throws IndexOutOfBoundsException
	 *             if <code>repeat &lt; 0</code>
	 */
	private static String padding(int repeat, char padChar) {
		// be careful of synchronization in this method
		// we are assuming that get and set from an array index is atomic
		String pad = PADDING[padChar];
		if (pad == null) {
			pad = String.valueOf(padChar);
		}
		while (pad.length() < repeat) {
			pad = pad.concat(pad);
		}
		PADDING[padChar] = pad;
		return pad.substring(0, repeat);
	}

	/**
	 * <p>
	 * Right pad a String with spaces (' ').
	 * </p>
	 *
	 * <p>
	 * The String is padded to the size of <code>size</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.rightPad(null, *)   = null
	 * StringUtils.rightPad(&quot;&quot;, 3)     = &quot;   &quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 3)  = &quot;bat&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 5)  = &quot;bat  &quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 1)  = &quot;bat&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, -1) = &quot;bat&quot;
	 * </pre>
	 *
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @return right padded String or original String if no padding is
	 *         necessary, <code>null</code> if null String input
	 */
	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	/**
	 * <p>
	 * Right pad a String with a specified character.
	 * </p>
	 *
	 * <p>
	 * The String is padded to the size of <code>size</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.rightPad(null, *, *)     = null
	 * StringUtils.rightPad(&quot;&quot;, 3, 'z')     = &quot;zzz&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 3, 'z')  = &quot;bat&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 5, 'z')  = &quot;batzz&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 1, 'z')  = &quot;bat&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, -1, 'z') = &quot;bat&quot;
	 * </pre>
	 *
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @param padChar
	 *            the character to pad with
	 * @return right padded String or original String if no padding is
	 *         necessary, <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(padding(pads, padChar));
	}

	/**
	 * <p>
	 * Right pad a String with a specified String.
	 * </p>
	 *
	 * <p>
	 * The String is padded to the size of <code>size</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.rightPad(null, *, *)      = null
	 * StringUtils.rightPad(&quot;&quot;, 3, &quot;z&quot;)      = &quot;zzz&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 3, &quot;yz&quot;)  = &quot;bat&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 5, &quot;yz&quot;)  = &quot;batyz&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 8, &quot;yz&quot;)  = &quot;batyzyzy&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 1, &quot;yz&quot;)  = &quot;bat&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, -1, &quot;yz&quot;) = &quot;bat&quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 5, null)  = &quot;bat  &quot;
	 * StringUtils.rightPad(&quot;bat&quot;, 5, &quot;&quot;)    = &quot;bat  &quot;
	 * </pre>
	 *
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @param padStr
	 *            the String to pad with, null or empty treated as single space
	 * @return right padded String or original String if no padding is
	 *         necessary, <code>null</code> if null String input
	 */
	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	/**
	 * Removes white space from both ends of this string, handling null by
	 * returning an empty string.
	 *
	 * @see String#trim()
	 * @param str
	 *            the string to check
	 * @return the trimmed text (never <code>null</code>)
	 */
	public static String clean(String str) {
		return (str == null ? "" : str.trim());
	}
	/**
	 * Removes white space from both ends of this string, handling null by
	 * returning an empty string.
	 *
	 * @see String#trim()
	 * @param str
	 *            the string to check
	 * @return the trimmed text (never <code>null</code>)
	 */
	public static String clean(Object str) {
		return (str == null ? "" : String.valueOf(str).trim());
	}
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String chkNullTrim(Object obj) {
		if (obj == null) {
			return "";
		}
		if (((String) obj).equalsIgnoreCase("null")) {
			return "";
		}
		if("".equals(((String)obj).trim())){
			return "";
		}
		return ((String) obj).trim();
	}

	/**
	 * Removes white space from both ends of this string, handling null by
	 * returning null.
	 *
	 * @see String#trim()
	 * @param str
	 *            the string to check
	 * @return the trimmed text (or <code>null</code>)
	 */
	public static String trim(String str) {
		return (str == null ? null : str.trim());
	}

	public static String trim(String str, boolean flag) {
		str = trim(str);
		if (!flag) {
			return str;
		}

		return defaultString(str);
	}

	/**
	 * Checks if a String is non null and is not empty (length > 0).
	 *
	 * @param str
	 *            the string to check
	 * @return true if the String is non-null, and not length zero
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null && str.trim().length() > 0);
	}

	/**
	 * Checks if a (trimmed) String is null or empty.
	 *
	 * @param str
	 *            the string to check
	 * @return true if the String is null, or length zero once trimmed
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * Validate if a string array is null or empty.
	 *
	 * @param strs
	 *            string arrays to be checked
	 * @return true if the string array is null or is a zero-length array.
	 */
	public static boolean isEmpty(String[] strs) {
		if ((strs == null) || (strs.length == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * Return either the passed in String, or if it is null, then an empty
	 * String.
	 *
	 * @param str
	 *            the string to check
	 * @return the passed in string, or blank if it was null
	 */
	public static String defaultString(String str) {
		return defaultString(str, "");
	}

	/**
	 * Return either the passed in String, or if it is null, then a passed in
	 * default String.
	 *
	 * @param str
	 *            the string to check
	 * @param defaultString
	 *            the default string to return is str is null
	 * @return the passed in string, or the default if it was null
	 */
	public static String defaultString(String str, String defaultString) {
		return (str == null) ? defaultString : str;
	}

	/**
	 * Check if the targeted strArray contains the specified strElement
	 *
	 * @param strArray
	 *            the targeted array to be checked
	 * @param strElment
	 *            string element to be searched in the strArray
	 * @return true if strElement existed in strArray
	 */
	public static boolean contains(String[] strArray, String strElement) {
		boolean flag = false;
		if (!isEmpty(strArray)) {
			for (int i = 0; i < strArray.length; i++) {
				String tempStr = strArray[i];
				if (defaultString(strElement).equals(defaultString(tempStr))) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}


	public static String defaultString(Object o) {
		return (o == null) ? "" : o.toString();
	}


	/**
	 * This method checks whether two strings are equal. Assumption: null is
	 * equal to empty string.
	 *
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqual(String str1, String str2) {
		if (isEmpty(str1) && isEmpty(str2)) {
			return true;
		} else if (str1 != null) {
			return str1.equals(str2);
		} else {
			return false;
		}
	}


	/**
	 * Judge the input String is Number whether or not according to the RULE as
	 * follows The RULE: the String include 0-9 or has one "." or has some ",",
	 * and the "," must in front of "."
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		final String NUMBER_RULE = "([0-9,\\,]*\\.?[0-9]*)";
		Pattern pattern = Pattern.compile(NUMBER_RULE);
		Matcher mch = pattern.matcher(str);
		return mch.matches();
	}

	public static boolean equals(String s1, String s2) {
		return s1 == null && s2 == null || s1 != null && s1.equals(s2);
	}
	
	/**
	 * 判断是否为浮点数值
	 * @param str
	 * @return
	 */
	public static boolean isFloatVal(String str){
		final String NUMBER_RULE = "^[1-9]\\d*(\\.\\d+)?$|^0(\\.\\d+)?$";
		Pattern pattern = Pattern.compile(NUMBER_RULE);
		Matcher mch = pattern.matcher(str);
		return mch.matches();
	}

	/**
	 * <p>
	 * Left pad a String with a specified character.
	 * </p>
	 *
	 * <p>
	 * The String is padded to the size of <code>size</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.leftPad(null, *, *)     = null
	 * StringUtils.leftPad(&quot;&quot;, 3, 'z')     = &quot;zzz&quot;
	 * StringUtils.leftPad(&quot;bat&quot;, 3, 'z')  = &quot;bat&quot;
	 * StringUtils.leftPad(&quot;bat&quot;, 5, 'z')  = &quot;zzbat&quot;
	 * StringUtils.leftPad(&quot;bat&quot;, 1, 'z')  = &quot;bat&quot;
	 * StringUtils.leftPad(&quot;bat&quot;, -1, 'z') = &quot;bat&quot;
	 * </pre>
	 *
	 * @param source the String to pad out, may be null
	 * @param size the size to pad to
	 * @param pad the character to pad with
	 * @return left padded String or original String if no padding is
	 *         necessary, <code>null</code> if null String input
	 */
	public static String leftPad(String source,int size,String pad){
		if(source == null){
			return null;
		}
		if(size <= source.length()){
			return source;
		}
		while(size > source.length()){
			source = pad + source;
		}
		return source;
	}
	
	/**
	 * <pre>
	 * 生成六位随机数,不足<code>size</code>位，会在前面补0
	 * 如：4323  ->   004323
	 * </pre>
	 * @param size 位数
	 * @return
	 */
	public static String random(int size){
		SecureRandom sr = new SecureRandom();
		int randomNum = sr.nextInt(100000);
		return leftPad(String.valueOf(randomNum),size,"0");
	}
	
	/**
	 * 获取营销的开关
	 * @param sellChannel
	 * @param num
	 * @return
	 */
	public static boolean getSellChannel(String sellChannel,int num){
		char flag = ' ';
		try {
			flag = sellChannel.charAt(num);
		} catch (Exception e) {
		}
		return flag=='1'?true:false;
		
	}
	
	/**
	 * 获取本机IP地址
	 * @return
	 */
	public static String getLocalHost(){
		InetAddress address;
		try {
			address = Inet4Address.getLocalHost();
			String hostAddress = address.getHostAddress();
			return hostAddress;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static Map<String,String> convertToMap(String str){
		Map<String,String> map = new HashMap<String,String>();
		String [] array = str.split("&");
		for(String sub:array){
			map.put(sub.split("=")[0], sub.split("=")[1]);
		}
		return map;
	}
	
	/**
	 * 将json格式数据转换成Map格式
	 * @param json
	 * @return Map对象
	 */
	@SuppressWarnings("unchecked")
/*	public static Map<String, Object> jsonToMap(String json){
		if(JSONUtils.mayBeJSON(json)){
			JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(json);
			JsonConfig config = new JsonConfig();
			config.setRootClass(HashMap.class);
			return (HashMap<String, Object>)JSONSerializer.toJava(jsonObject,config);
		} else {
			return null;
		}		
	}*/
	
	/**
	 * 格式化成yyyyMMddhhmmss
	 * @param str
	 * @return
	 */
	public static String convertToTimeStr(String str){
		if(str.contains("-")){
			str = str.replace("-", "");
		}
		if(str.contains(":")){
			str = str.replace(":", "");
		}
		if(str.contains(" ")){
			str = str.replace(" ", "");
		}
		return str;
	}
	
	/**
	 * 转换网站 网银 其他渠道
	 * @param channel
	 * @return
	 */
	public static String convertChannel(String channel){
		if("00".equals(channel)){
			return "网站";
		}else if("01".equals(channel)){
			return "网银";
		}else if("11".equals(channel)){
			return "手机";
		}else if("99".equals(channel)){
			return "全渠道";
		}
		return "其他";
	}
	
	/**
	 * 转换客户类型
	 * @param channel
	 * @return
	 */
	public static String convertCustType(String cust_type){
		if("00".equals(cust_type)){
			return "客户访问";
		}else if("01".equals(cust_type)){
			return "匿名访问";
		}
		return null;
	}
	
	/**
	 * 活动状态转中文
	 * @param channel
	 * @return
	 */
	public static String convertActivityStatus(String activity_status){
		if("00".equals(activity_status)){
			return "待执行";
		}else if("01".equals(activity_status)){
			return "执行中";
		}else if("02".equals(activity_status)){
			return "已结束";
		}
		return "全部";
	}
	
	/**
	 * 索引状态
	 * @param label_sts
	 * @return
	 */
	public static String convertLabelSts(String label_sts){
		if("00".equals(label_sts)){
			return "未启用";
		}else if("01".equals(label_sts)){
			return "已启用";
		}else if("02".equals(label_sts)){
			return "删除";
		}else if("03".equals(label_sts)){
			return "已过期";
		}else if("".equals(label_sts) || label_sts == null){
			return "全部";
		}
		return label_sts;
	}
	
	/**
	 * 审核状态
	 * @param audit_sts
	 * @return
	 */
	public static String convertAuditSts(String audit_sts){
		if("00".equals(audit_sts)){
			return "待审核";
		}else if("01".equals(audit_sts)){
			return "已审核";
		}else if("02".equals(audit_sts)){
			return "已驳回";
		}else if("".equals(audit_sts) || audit_sts == null){
			return "全部";
		}
		return audit_sts;
	}
	
	/**
	 * 同步状态
	 * @param sync_sts
	 * @return
	 */
	public static String convertSyncSts(String sync_sts){
		if("00".equals(sync_sts)){
			return "未同步";
		}else if("01".equals(sync_sts)){
			return "已同步";
		}else if("".equals(sync_sts) || sync_sts == null){
			return "全部";
		}
		return sync_sts;
	}
	/**
	 * 索引类型（白名单索引、组合索引）
	 * @param label_type
	 * @return
	 */
	public static String convertLabelType(String label_type){
		if("3".equals(label_type)){
			return "组合索引";
		}else if("4".equals(label_type)){
			return "白名单索引";
		}
		return "全部";
	}
	/**
	 * 传入一个String，返回一个5位数的String
	 */
	public static String changeToSix(String a){
		if (a.length()==1) {
			a="00000"+a;
		}else if (a.length()==2) {
			a="0000"+a;
		}else if (a.length()==3) {
			a="000"+a;
		}else if (a.length()==4) {
			a="00"+a;
		}else if (a.length()==5){
			a="0"+a;
		}
		return a;
	}
	
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			token = token.trim();
			if (token.length() > 0) {
				if (token.startsWith("(")) {
					/*tokens.add("(");
					token = token.substring(1, token.length()).trim();
					if (token.length() > 0) {
						tokens.add(token);
					}*/
					
					String ad = "";
					while ((token.contains("(")) && (token.startsWith("("))) {
						token = token.substring(1, token.length()).trim();
						ad = ad + "(";
					}
					tokens.add(ad);
					if (token.length() > 0) {
						tokens.add(token);
					}
					
				} else if (token.endsWith(")")) {
					String ad = "";
					while ((token.contains(")")) && (token.endsWith(")"))) {
						token = token.substring(0, token.length() - 1).trim();

						ad = ad + ")";
					}
					tokens.add(token);
					tokens.add(ad);
				} else {
					tokens.add(token);
				}
			}
		}
		return (String[]) tokens.toArray(new String[tokens.size()]);
	}
	
	public static String spendTime(long startTime, long endtime) {
	    return "总耗时: " + (endtime - startTime) / 3600000L + " 小时  " 
	    		+ (endtime - startTime) % 3600000L / 60000L + " 分 " 
	    		+ (endtime - startTime) % 60000L / 1000L + " 秒 " 
	    		+ (endtime - startTime) % 1000L + " 毫秒";
	}
	
}
