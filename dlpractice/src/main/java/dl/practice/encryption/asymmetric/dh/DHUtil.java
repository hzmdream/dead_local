package dl.practice.encryption.asymmetric.dh;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

/**
 * @author houzm
 * @time 2017年3月20日下午8:42:28
 * 非对称加密---DH算法
 */
/**
 * @author houzm
 * @time 2017年4月8日下午5:48:48
 * @description 
 */
/**
 * @author houzm
 * @time 2017年4月8日下午5:49:32
 * @description 
 */
public class DHUtil {

	public static final String PUBLIC_KEY = "DHPublicKey";
	public static final String PRIVATE_KEY = "DHPrivateKey";
	
	/**
	 * @author houzm
	 * @time 2017年4月8日下午5:48:56
	 * @description 甲方初始化并返回密钥对
	 * @return
	 * @throws Exception
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> initKey() throws Exception{
		/**
		 * 生成秘钥对
		 */
		//实例化密钥对生成器
		KeyPairGenerator pairGen = KeyPairGenerator.getInstance("DH");
		//初始化密钥对生成器
		pairGen.initialize(1024);
		//生成密钥对
		KeyPair keyPair = pairGen.generateKeyPair();
		//得到甲方公钥和甲方私钥
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
		DHPrivateKey privateKey  = (DHPrivateKey) keyPair.getPrivate();
		//将公钥和私钥封装在Map中，方便以后使用
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	/**
	 * @author houzm
	 * @time 2017年4月8日下午5:50:16
	 * @description 乙方根据甲方公钥初始化并返回密钥对
	 * @param key
	 * @return
	 * @throws Exception
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> initKey(byte[] key) throws Exception{
		//将甲方公钥从字节数组转换为PublicKey
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
		//实例化秘钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance("DH");
		//产生甲方公钥publicKey
		DHPublicKey dhPublicKey = (DHPublicKey) keyFactory.generatePublic(keySpec);
		//剖析甲方公钥，得到参数
		DHParameterSpec dhParameterSpec = dhPublicKey.getParams();
		//实例化秘钥对生成器
		KeyPairGenerator pairGen = KeyPairGenerator.getInstance("DH");
		//用甲方公钥初始化密钥对生成器
		pairGen.initialize(dhParameterSpec);
		//产生密钥对
		KeyPair keyPair = pairGen.generateKeyPair();
		//得到乙方公钥和乙方私钥
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
		DHPrivateKey privateKey  = (DHPrivateKey) keyPair.getPrivate();
		//将公钥和私钥封装在Map中,方便之后使用
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
		
	}
	
	/**
	 * @author houzm
	 * @time 2017年4月8日下午6:02:19
	 * @description 根据对方的公钥和自己的私钥生成本地秘钥
	 * @param publicKey
	 * @param privateKey
	 * @return
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] getSecretKey(byte[] publicKey,byte[] privateKey) throws Exception{
		//实例化秘钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance("DH");
		//将公钥从字节数组转换为PublicKey
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKey);
		PublicKey pubkey = keyFactory.generatePublic(pubKeySpec);
		//将私钥从字节数组转换为privateKey
		PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(privateKey);
		PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);
		//准备根据以上公钥和私钥生成本地秘钥SecretKey
		
		//先实例化KeyAgreement
		KeyAgreement keyAgree = KeyAgreement.getInstance("DH");
		//用自己的私钥初始化KeyAgreement
		keyAgree.init(priKey);
		//结合对方的私钥进行运算
		keyAgree.doPhase(pubkey, true);
		//开始生成本地秘钥SecretKey秘钥算法为对称密码算法
		SecretKey secretKey = keyAgree.generateSecret("AES");//DES 3DES AES都可以
		return secretKey.getEncoded();
	}
	
	/**
	 * @author houzm
	 * @time 2017年4月8日下午6:14:49
	 * @description 从map中获得公钥
	 * @param keyMap
	 * @return
	 * @return byte[]
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap){
		DHPublicKey key = (DHPublicKey) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	};
	
	/**
	 * @author houzm
	 * @time 2017年4月8日下午6:15:01
	 * @description 从map中获得私钥
	 * @param keyMap
	 * @return
	 * @return byte[]
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap){
		DHPrivateKey key = (DHPrivateKey) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	};
	
}
