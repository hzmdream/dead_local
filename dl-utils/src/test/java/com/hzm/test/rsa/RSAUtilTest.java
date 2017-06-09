package com.hzm.test.rsa;

import org.junit.Test;

import dl.dlutils.utils.encrypt.asymmetric.rsa.RSAUtil;


public class RSAUtilTest {

	@Test
	public void getKeyPairTest() throws Exception{
		
		//使用公钥加密密文
		String encrypt = RSAUtil.encrypt("houzm_test");
		System.out.println(">>>密文>>>"+encrypt);
//		String encrypt = "Nx2NOnr3wk/ph/qqmgy40sD0aMOq6JKGisRBDTsrQnm3LL5Nvk+mgE37Gz5PQpR4vzFhbxrZGgvX"+"\n"+
//		"iEaC/e3Z8NtaCuxe0CAUyfKKj10fkL2tkPiuWYpO5Uxq6jKKLX43q39dxCfFEjnAChYUsusNt/Px"+"\n"+
//		"uBEZ/dkFjqTqSUDXWGo=";
		//使用秘钥解密密文
//		String decrypt = RSAUtil.decrypt("");
//		System.out.println(">>>解密>>>"+decrypt);
	}
	
	
}
