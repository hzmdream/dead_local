package com.hzm.test.encryption.dh;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import dl.practice.encryption.asymmetric.dh.DHUtil;

public class DHUtilTest {
	
	@Test
	public void testDH() throws Exception{
		//生成甲方密钥对
		Map<String, Object> keyPair = DHUtil.initKey();
		//打印甲方秘钥对
		byte[] pubKey = DHUtil.getPublicKey(keyPair);
		byte[] priKey = DHUtil.getPrivateKey(keyPair);
		System.out.println(Arrays.toString(pubKey));
		System.out.println(Arrays.toString(priKey));
		
		//根据甲方公钥生成乙方秘钥对
		Map<String, Object> keyPairYi = DHUtil.initKey(pubKey);
		//打印乙方秘钥对
		byte[] pubKey2 = DHUtil.getPublicKey(keyPairYi);
		byte[] priKey2 = DHUtil.getPrivateKey(keyPairYi);
		System.out.println(Arrays.toString(pubKey2));
		System.out.println(Arrays.toString(priKey2));
		
		//甲方根据乙方公钥和自己的私钥生成本地秘钥
		byte[] secretKey = DHUtil.getSecretKey(pubKey2, priKey);
		//乙方根据甲方公钥和自己的私钥生成本地秘钥
		byte[] secretKey2 = DHUtil.getSecretKey(pubKey, priKey2);
		
		System.out.println(Arrays.toString(secretKey));
		System.out.println(Arrays.toString(secretKey2));
	}
	
}
