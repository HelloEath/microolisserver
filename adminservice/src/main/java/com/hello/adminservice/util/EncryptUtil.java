package com.hello.adminservice.util;
/**
 * 
 * @deprecated DES加密工具类
 * @author wuyangbo1
 * @date 2015-08-24 10:36
 * 
 * <p>修改历史 </p>
 * <p>序号	日期			修改人		修改原因	</p>
 * <p>1		20150821	wuyangbo1	初始建立	</p>
 * <p>	</p>
 * <p>	</p>
 */

import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;


public class EncryptUtil {
	private static String strDefaultKey = "hnzt";

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 * @author <a href="mailto:zhangji@aspire-tech.com">ZhangJi</a>
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 默认构造方法，使用默认密钥
	 * 
	 * @throws Exception
	 */
	public EncryptUtil() throws Exception {
		this(strDefaultKey);
	}
	
	public static String encrypt(String str,String key) throws Exception {
		EncryptUtil d=new EncryptUtil(key);
		return d.encrypt(str);
	}
	public static String decrypt(String str,String key) throws Exception {
		EncryptUtil d=new EncryptUtil(key);
		return d.decrypt(str);
	}

	/**
	 * 指定密钥构造方法
	 * 
	 * @param strKey
	 *            指定的密钥
	 * @throws Exception
	 */
	public EncryptUtil(String strKey) throws Exception {
		//Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 加密字符串
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}
	
	public int encrypt(InputStream is,OutputStream os) {
		try {
			byte[] buf = new byte[is.available()];
			byte[] encryptBuf;
			int num,size;			
			size=0;
			while ((num = is.read(buf)) != -1) {
				size+=num;
				encryptBuf=encrypt(ArrayUtils.subarray(buf,0,num));
				os.write(encryptBuf, 0, encryptBuf.length);				
			}				
			// 关闭流,必须关闭所有输入输出流.保证输入输出完整和释放系统资源		
			is.close();
			os.close();
			return size;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int decrypt(InputStream is,OutputStream os) {
		try {
			byte[] buf = new byte[1024];
			byte[] decryptBuf;
			int num,size;			
			size=0;
			while ((num = is.read(buf)) != -1) {
				decryptBuf=decrypt(ArrayUtils.subarray(buf,0,num));
				os.write(decryptBuf, 0, decryptBuf.length);	
				size+=decryptBuf.length;
			}				
			// 关闭流,必须关闭所有输入输出流.保证输入输出完整和释放系统资源			
			return size;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
