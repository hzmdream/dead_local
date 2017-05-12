package dl.practice.encryption.symmetry.tripledes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import dl.practice.encryption.symmetry.util.ByteToHexUtil;

/**
 * 3des加密
 * Created by zhangweixiang on 4/17/2016.
 */
public class TripleDESUtil {

    /**
     * 生成3desckey
     * @param type
     * @return
     * @throws Exception
     */
    public static byte[] generateKey(String type) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(type);
        keyGenerator.init(112);//128 168
        SecretKey secretKey = keyGenerator.generateKey();
        return  secretKey.getEncoded();
    }

    /**
     * 通过指定的3deskey加密
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
     * 3des解密
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
        String type = "DESede";// DES/ECB/PKCS5Padding
        byte[] key = TripleDESUtil.generateKey(type);
        byte[] encData = TripleDESUtil.encrypt(data.getBytes(),key,type);
        String encDataStr = ByteToHexUtil.bytesToHexString(encData);
        System.out.println(data+">>3des encrypt>>"+encDataStr);

        byte[] decData = TripleDESUtil.decrypt(encData,key,type);
        System.out.println(encDataStr+">>3des decrypt>>"+new String(decData));
    }
}
