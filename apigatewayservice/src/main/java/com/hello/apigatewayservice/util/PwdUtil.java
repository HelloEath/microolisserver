package com.hello.apigatewayservice.util;
/**
 * 
 * @deprecated 配置文件密码工具类
 * @author wuyangbo1
 * @date 2015-08-24 10:36
 * 
 * <p>修改历史 </p>
 * <p>序号	日期			修改人		修改原因	</p>
 * <p>1		20150824	wuyangbo1	初始建立	</p>
 * <p>	</p>
 * <p>	</p>
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PwdUtil {
	private static Log logger = LogFactory.getLog(PwdUtil.class);
	
	/**
	 * Rerturn the After Eccrpipt
	 * @param value
	 * @return
	 */
	public static String returnEncriptPwd(String value) throws Exception{  
		EncryptUtil des;
		String encriptPwd = "null";
		try {
			des = new EncryptUtil();
			encriptPwd = des.encrypt(value);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("set new password failed");
			logger.error(e);
			e.printStackTrace();
			throw(e);
		}
		return encriptPwd;

	}

	
	/**
	 * 
	 * 解密数据库密码
	 * 
	 */
	public static String getDecriptPwd(String encryptStr) throws Exception{    
		EncryptUtil des;
		String str = "";
		try {
			des = new EncryptUtil();
			str =  des.decrypt(encryptStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
			throw(e);
		}
		
		return str;
    }  
	
	/**
	 * 加密数据
	 * @param encryptStr
	 * @return
	 */
	public static String getEncrypt(String encryptStr) {    
		EncryptUtil des;
		String str = "";
		try {
			des = new EncryptUtil();
			//des = EncryptUtil.getInstance();
			str =  des.encrypt(encryptStr);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return str;
    }
	
	/**
	 * 转换rowkey
	 * 将rowkey加密后的字符串倒序后取前20位  + "_" + rowkey
	 * @param rowkey
	 * @return
	 */
	public static String transCustRowkey(String rowkey){
		StringBuffer sb = new StringBuffer(PwdUtil.getEncrypt(rowkey));
		String newRowkey = "";
		if(sb.length() >=20 ){
			newRowkey = sb.reverse().substring(0, 20).toString() + "_" + rowkey;
		}else{
			newRowkey = sb.reverse().toString() + "_" + rowkey;
		}
		return newRowkey;
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(PwdUtil.getEncrypt("admin"));//加密
		System.out.println(PwdUtil.getDecriptPwd("845741f40ecccf9f"));//解密
	}
}
