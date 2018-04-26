package com.sl.www.test;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Test {
	
	public static void main(String[] args) {
		String password="123456";
		
		String md5Password=DigestUtils.md5Hex(password);
		System.out.println(md5Password);
	}

}
