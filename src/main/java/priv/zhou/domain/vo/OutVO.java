package priv.zhou.domain.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import priv.zhou.params.OutVOEnum;

/**
 * Created by Andy on 2016/11/9.
 * 全局返回VO
 */
@Getter
@Setter
@Accessors(chain = true)
@Slf4j
public class OutVO {


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
    private Object data;


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
    private void setEnum(OutVOEnum outVoEnum) {
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
    public static OutVO fail(OutVOEnum outVoEnum) {
        return new OutVO(outVoEnum);
    }


    /**
     * 返回api成功
     */
    public static OutVO success() {
        return new OutVO(OutVOEnum.SUCCESS);
    }


    /**
     * 返回api成功
     */
    public static OutVO success(Object data) {
        return new OutVO(OutVOEnum.SUCCESS).setData(data);
    }


}
