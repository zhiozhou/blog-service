package priv.zhou.misc;

/**
 * 全局返回Code枚举类
 *
 * @author zhou
 * @since 2019.03.11
 */
public enum OutVOEnum {

    SUCCESS("0000", "请求成功"),

    EMPTY_PARAM("0001", "参数为空"),

    FAIL_PARAM("0002", "参数错误"),

    FAIL_OPERATION("0003", "操作失败"),

    NOT_FOUND("0004", "数据不存在"),

    NEED_INIT("0100", "尚未初始化"),

    VERSION_DEPRECATED("0200", "版本过时"),

    LATER_RETRY("9001", "请稍后重试"),

    OFTEN_OPERATION("9002", "操作频繁"),

    ACCESS_BLOCK("9003", "禁止访问"),

    ERROR_SYSTEM("9999", "系统异常");


    OutVOEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    /**
     * 异常编码
     */
    private final String code;
    /**
     * 异常信息
     */
    private final String info;

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
