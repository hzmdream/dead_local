package com.hzm.test.encryption.rsa;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import org.junit.Test;

import dl.practice.encryption.asymmetric.rsa.basic.RSAUtil;

public class RSAUtilTest {

	@Test
	public void RSAEncryptTest() throws Exception{
		
		//获取秘钥对
		Map<String, Object> keyPair = RSAUtil.initKey();
		RSAPublicKey publicKey = RSAUtil.getPublicKey(keyPair);
		RSAPrivateKey privateKey = RSAUtil.getPrivateKey(keyPair);
		//打印显示
		System.out.println("---------------------------------------------------");
		System.out.println(publicKey);
		System.out.println("---------------------------------------------------");
		System.out.println(privateKey);
		System.out.println("---------------------------------------------------");
		
		//对houzm加密
		String houzm = "houzm";
		byte[] encrypt = RSAUtil.encrypt(houzm.getBytes(), publicKey);
		System.out.println("encrypt >>>密文>>> "+encrypt);
		byte[] decrypt = RSAUtil.decrypt(encrypt, privateKey);
		System.out.println("decrypt >>>明文>>> "+new String(decrypt));
	}
	
	
}
