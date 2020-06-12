package priv.zhou.domain.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.domain.po.AccessLogPO;

/**
 * 访问日志 数据传输模型
 *
 * @author zhou
 * @since 2020.06.08
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class AccessLogDTO extends DTO<AccessLogPO> {


    /**
     * 访客id
     */
    private String token;

    /**
     * 主机
     */
    private String host;

    /**
     * 请求接口
     */
    private String api;

    /**
     * 请求参数
     */
    private JSONObject param;

    /**
     * 用户代理
     */
    private String userAgent;

    @Override
    public AccessLogPO toPO() {
        AccessLogPO po = super.toPO();
        if(null != param && !param.isEmpty()){
            po.setParam(param.toJSONString());
        }
        return po;
    }
}
