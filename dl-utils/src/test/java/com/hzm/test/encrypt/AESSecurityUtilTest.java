package com.hzm.test.encrypt;

import org.junit.Test;

import dl.dlutils.utils.encrypt.copy.AESSecurityUtil;

public class AESSecurityUtilTest {

	@Test
	public void getKey(){
		try {
			String keyStr = AESSecurityUtil.getKeyStr();
			System.out.println(keyStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void encry(){
		String key = "m71qQKYOGQ9omsB3jppTNQ==";
		try {
			String username = AESSecurityUtil.encrypt("houzm_test", key);
			String password = AESSecurityUtil.encrypt("houzm", key);
			String  schema= AESSecurityUtil.encrypt("dead_local", key);
			System.out.println("username>>>"+username);
			System.out.println("password>>>"+password);
			System.out.println("schema>>>"+schema);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
