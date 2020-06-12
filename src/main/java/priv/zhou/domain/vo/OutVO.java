package priv.zhou.domain.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.misc.NULL;
import priv.zhou.misc.OutVOEnum;

import java.util.List;

/**
 * Created by Andy on 2016/11/9.
 * 全局返回VO
 */
@Getter
@Setter
@Accessors(chain = true)
public class OutVO<T> {

    /**
     * 系统状态
     */
    private String code;
    /**
     * 系统返回信息
     */
    private String info;
    /**
     * 返回数据
     */
    private T data;

    public OutVO() {
    }

    public OutVO(OutVOEnum outVoEnum) {
        setEnum(outVoEnum);
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * 设置枚举信息
     */
    public void setEnum(OutVOEnum outVoEnum) {
        this.code = outVoEnum.getCode();
        this.info = outVoEnum.getInfo();
    }

    /**
     * 状态玛是否错误
     */
    @JsonIgnore
    public boolean isFail() {
        return !OutVOEnum.SUCCESS.getCode().equals(this.code);
    }


    /**
     * 返回api错误
     */
    public static <E> OutVO<E> fail(OutVOEnum outVoEnum) {
        return new OutVO<>(outVoEnum);
    }


    /**
     * 返回api成功
     */
    public static OutVO<NULL> success() {
        return new OutVO<>(OutVOEnum.SUCCESS);
    }


    /**
     * 返回api成功
     */
    public static <T> OutVO<T> success(T data) {
        return new OutVO<T>(OutVOEnum.SUCCESS).setData(data);
    }


    public static <T> OutVO<ListVO<T>> list(List<T> list, boolean hasMore) {
        return success(new ListVO<T>().setList(list).setHasMore(hasMore));
    }

}
