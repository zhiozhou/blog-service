package priv.zhou.tools;

import java.util.Random;

/**
 * 随机工具优化版
 * @author zhou
 */
@SuppressWarnings("unused")
public class RandomUtil {

    private static final String NUMBER_CHAR = "0123456789";
    private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALL_CHAR = NUMBER_CHAR + LETTER_CHAR;


    /**
     * 生成定长的 数字 随机数
     *
     * @param length 长度
     */
    public static String number(int length) {
        return generate(NUMBER_CHAR, length);
    }

    /**
     * 获取定长的随机字母
     *
     * @param length 长度
     */
    public static String letter(int length) {
        return generate(LETTER_CHAR, length);
    }

    /**
     * 获取定长的小写字母随机数
     *
     * @param length 随机数长度
     */
    public static String lowerLetter(int length) {
        return letter(length).toLowerCase();
    }

    /**
     * 获取定长的随机数，只包含大写字母
     *
     * @param length 随机数长度
     */
    public static String upperLetter(int length) {
        return letter(length).toUpperCase();
    }


    /**
     * 获取定长的随机数，包含大小写、数字
     *
     * @param length 随机数长度
     */
    public static String chars(int length) {
        return generate(ALL_CHAR, length);
    }


    private static String generate(String str, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(str.charAt(random.nextInt(str.length())));
        }
        return sb.toString();
    }


}
