package priv.zhou.tools;

import java.security.MessageDigest;

/**
 * @author zhou
 * @since 2019.03.11
 */
public class Md5Util {


	/**
	 * MD5加密
	 */
	public static String encrypt(String data) throws Exception {
		StringBuilder sb = new StringBuilder();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] bytes = md5.digest(data.getBytes());
		for (byte b : bytes) {
			int bt = b & 0xff;
			if (bt < 16) {
				sb.append(0);
			}
			sb.append(Integer.toHexString(bt));
		}
		return sb.toString();
	}
}
