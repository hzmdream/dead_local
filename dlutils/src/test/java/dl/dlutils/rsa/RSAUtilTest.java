package dl.dlutils.rsa;

import org.junit.Test;

import dl.dlutils.utils.rsa.RSAUtil;


public class RSAUtilTest {

	@Test
	public void getKeyPairTest() throws Exception{
		
		//使用公钥加密密文
		String encrypt = RSAUtil.encrypt("houzm");
		System.out.println(">>>密文>>>"+encrypt);
		//使用秘钥解密密文
		String decrypt = RSAUtil.decrypt(encrypt);
		System.out.println(">>>解密>>>"+decrypt);
	}
	
	
}
