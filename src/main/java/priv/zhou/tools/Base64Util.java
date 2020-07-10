package priv.zhou.tools;

import java.util.Base64;


public class Base64Util {

    /**
     * BASE64解密
     */
    public static byte[] decode(String key) throws Exception {
        return null == key ? null : Base64.getDecoder().decode(key);
    }


    /**
     * BASE64加密
     */
    public static String encode(byte[] key) {
        return null == key ? null : Base64.getEncoder().encodeToString(key);
    }
}
