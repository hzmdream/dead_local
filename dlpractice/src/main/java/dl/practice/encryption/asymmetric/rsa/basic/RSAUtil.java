package dl.practice.encryption.asymmetric.rsa.basic;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * @author houzm
 * @time 2017年4月9日下午4:39:51
 * @description RSA非对称加密工具类  私钥中的p,q是核心，不能被人获取到
 */
public class RSAUtil {
	
	public static final String PUBLIC_KEY = "RSAPublicKey";
	public static final String PRIVATE_KEY = "RSAPrivateKey";
	
	/**
	 * @author houzm
	 * @time 2017年4月9日下午4:19:45
	 * @description 生成RSA的公钥和私钥
	 * @return
	 * @throws Exception
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> initKey() throws Exception{
		/**
		 * 生成秘钥对
		 */
		//实例化密钥对生成器
		KeyPairGenerator pairGen = KeyPairGenerator.getInstance("RSA");
		//初始化密钥对生成器
		pairGen.initialize(1024);//512-65536 但必须是64的倍数
		//生成密钥对
		KeyPair keyPair = pairGen.generateKeyPair();
		//得到甲方公钥和甲方私钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey  = (RSAPrivateKey) keyPair.getPrivate();
		//将公钥和私钥封装在Map中，方便以后使用
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	/**
	 * @author houzm
	 * @time 2017年4月9日下午4:25:51
	 * @description 公钥加密
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] data,RSAPublicKey publicKey) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] cipherBytes = cipher.doFinal(data);
		return cipherBytes;
	}
	/**
	 * @author houzm
	 * @time 2017年4月9日下午4:25:51
	 * @description 私钥加密
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] decrypt(byte[] data,RSAPrivateKey privateKey) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] plainBytes = cipher.doFinal(data);
		return plainBytes;
	}
	
	/**
	 * @author houzm
	 * @time 2017年4月9日下午4:22:30
	 * @description 从map中获得公钥
	 * @param keyMap
	 * @return
	 * @return RSAPublicKey
	 */
	public static RSAPublicKey getPublicKey(Map<String, Object> keyMap){
		RSAPublicKey key = (RSAPublicKey) keyMap.get(PUBLIC_KEY);
		return key;
	};
	
	/**
	 * @author houzm
	 * @time 2017年4月9日下午4:22:54
	 * @description 从map中获得私钥
	 * @param keyMap
	 * @return
	 * @return RSAPrivateKey
	 */
	public static RSAPrivateKey getPrivateKey(Map<String, Object> keyMap){
		RSAPrivateKey key = (RSAPrivateKey) keyMap.get(PRIVATE_KEY);
		return key;
	};
}
