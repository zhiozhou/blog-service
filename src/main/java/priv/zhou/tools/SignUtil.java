package priv.zhou.tools;

import java.util.Map;
import java.util.TreeMap;

/**
 * 签名工具优化版
 * @author zhou
 */
@SuppressWarnings("unused")
public class SignUtil {

	/**
	 * 统一使用，方便修改
	 */
	public static Map<String, Object> getTreeMap() {
		return new TreeMap<>();
	}


 	public static Map<String, Object> sign(Map<String, Object> map, String key) throws Exception {
		map.put("key", key);
		map.put("sign", sign(toString(map)));
		map.remove("key");
		return map;
	}


	/**
	 * map转换为字符串
	 */
	public static String toString(Map<String, Object> map) {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry entry : map.entrySet()) {
			builder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
		}
		return builder.deleteCharAt(0).toString();
	}

	/**
	 * zity签名
	 */
	private static String sign(String data) throws Exception {
		return Md5Util.encrypt(data);
	}


}
