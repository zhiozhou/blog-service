package priv.zhou.tools;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class Base64Util {


    /**
     * BASE64解密
     */
    public static byte[] decrypt(String key) throws Exception {
        return null == key ? null : new BASE64Decoder().decodeBuffer(key);
    }


    /**
     * BASE64加密
     */
    public static String encrypt(byte[] key) {
        return null == key ? null : new BASE64Encoder().encodeBuffer(key);
    }
}
