package priv.zhou.tools;

import java.security.MessageDigest;

/**
 *
 * @author zhou
 * @since 2019.03.11
 */
public class Md5Util {

	private final static String ALGORITHM = "MD5";

	public static String encrypt(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance(ALGORITHM).digest(string.getBytes());
		} catch (Exception e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}
}
