package priv.zhou.tools;

import java.util.regex.Pattern;

public class CheckUtil {


    /**
     * 不为邮箱
     */
    public static boolean notEmail(String email) {
        return !email(email);
    }

    /**
     * 是否为邮箱
     */
    public static boolean email(String email) {
        return null != email && Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches();
    }


    /**
     * 不为httpUrl
     */
    public static boolean notHttpUrl(String url) {
        return !httpUrl(url);
    }

    /**
     * 是否为httpUrl
     */
    public static boolean httpUrl(String url) {
        return null != url && Pattern.compile("^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$").matcher(url).matches();
    }

    /**
     * 不包含中文
     */
    public static boolean notHasChinese(String value) {
        return !hasChinese(value);
    }

    /**
     * 包含中文
     */
    public static boolean hasChinese(String value) {
        return null != value && Pattern.compile("[\u4e00-\u9fa5]").matcher(value).matches();
    }


    /**
     * 全为数字
     */
    public static boolean allNumber(String value) {
        return null != value && Pattern.compile("[0-9]*").matcher(value).matches();
    }

}
