package com.tax.core.util;

import java.security.MessageDigest;

/**
 * MD5Utils
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月22日 下午4:38:48
 * @version  v1.0
 */

public class MD5Utils {
	
	/**
	 * MD5加密方法
	 * @param str 明文
	 * @return 密文(32位)
	 */
	public static String getMD5(String str) throws Exception{
		/** 创建加密对象 */
		MessageDigest md = MessageDigest.getInstance("MD5");
		/** 加密 */
		md.update(str.getBytes("utf-8"));
		/** 获取加密后的内容 (16位的字符数组) */
		byte[] md5Bytes = md.digest();
		/*System.out.println("加密前：" + Arrays.toString(str.getBytes()));
		System.out.println("加密后：" + Arrays.toString(md5Bytes));*/
		String res = "";
		/** 把加密后字节数组转化成32位字符串 (把每一位转化成16进制的两位) */
		for (int i = 0; i < md5Bytes.length; i++){
			int temp = md5Bytes[i] & 0xFF;
			/** 把temp值转化成16进制的两位数，如果不够两位前面补零 */
			if (temp <= 0xF){
				res += "0";
			}
			res += Integer.toHexString(temp);
		}
		return res;
	}
	
	/*public static void main(String[] args) throws Exception {
		System.out.println(MD5Utils.getMD5("123456"));
	}*/
}
