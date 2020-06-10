package priv.zhou.tools;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhou
 * @since 2019.11.25
 */
public class ParseUtil {


    private ParseUtil() {

    }

    public static <T> T object(Object obj, Class<T> clazz) {
        return obj == null ? null : JSON.parseObject(obj.toString(), clazz);
    }

    public static <T> List<T> list(Object obj, Class<T> clazz) {
        return obj == null ? new ArrayList<>() : JSON.parseArray(obj.toString(), clazz);
    }

    /**
     * 解析integer
     * 为 空 返回 0
     */
    public static Integer integer(Object obj) {
        return obj != null ? obj instanceof Integer ? (Integer) obj : Integer.parseInt(obj.toString()) : 0;
    }

    /**
     * 解析long
     * 为 空 返回 0
     */
    public static Long longer(Object obj) {
        return obj != null ? obj instanceof Long ? (Long) obj : Long.parseLong(obj.toString()) : 0;
    }


    /**
     * 解析BigDecimal
     * 为 空 返回 0
     */
    public static BigDecimal bigDecimal(Object obj) {
        return obj != null ? obj instanceof BigDecimal ? (BigDecimal) obj : new BigDecimal(obj.toString()) : BigDecimal.ZERO;
    }


    /**
     * 拆箱Boolean
     * 为 空 返回 false
     */
    public static boolean unBox(Boolean obj) {
        return obj == null ? false : obj;
    }


    /**
     * 拆箱Long
     * 为 空 返回 0
     */
    public static long unBox(Long obj) {
        return obj == null ? 0L : obj;
    }

}
