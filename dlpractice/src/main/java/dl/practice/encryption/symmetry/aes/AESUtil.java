package dl.practice.encryption.symmetry.aes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import dl.practice.encryption.symmetry.util.ByteToHexUtil;

/**
 * aes加密/解密
 * Created by zhangweixiang on 4/17/2016.
 */
public class AESUtil {

    /**
     * 生成adesckey
     * @param type
     * @return
     * @throws Exception
     */
    public static byte[] generateKey(String type) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(type);
        keyGenerator.init(128);//默认为128位，如果使用192 256则需获取无政策文件(从oracle官网下载UnlimitedJECPolicyJDK7解压后将
                                //其中的2个jar拷贝到jre下的lib下的security中即可 )
        SecretKey secretKey = keyGenerator.generateKey();
        return  secretKey.getEncoded();
    }

    /**
     * 通过指定的aeskey加密
     * @param data 加密的数据
     * @param key 秘钥
     * @param type 加密方式
     * @return 加密信息
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,byte[] key,String type) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key,type);
        Cipher cipher = Cipher.getInstance(type);
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        return cipher.doFinal(data);
    }

    /**
     * aes解密
     * @param data 需要解密的数据
     * @param key 解密秘钥
     * @param type 类型
     * @return 解密后的结果
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,byte[] key,String type) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key,type);
        Cipher cipher = Cipher.getInstance(type);
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        String data = "test desc";
        String type = "AES";//AES/ECB/PKCS5Padding
        byte[] key = AESUtil.generateKey(type);
        byte[] encData = AESUtil.encrypt(data.getBytes(),key,type);
        String encDataStr = ByteToHexUtil.bytesToHexString(encData);
        System.out.println(data+">>aes encrypt>>"+encDataStr);

        byte[] decData = AESUtil.decrypt(encData,key,type);
        System.out.println(encDataStr+">>aes decrypt>>"+new String(decData));
    }
}
