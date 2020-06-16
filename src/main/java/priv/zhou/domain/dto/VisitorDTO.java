package priv.zhou.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import priv.zhou.domain.po.VisitorPO;

import java.util.Date;


/**
 * 访客 数据传输模型
 *
 * @author zhou
 * @since 2020.06.08
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitorDTO extends DTO<VisitorPO> {

    /**
     * 
     */
    private Integer id;

    /**
     * 名称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 站点
     */
    private String website;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 通知
     */
    private Integer notify;

    /**
     * 备注
     */
    private String remark;

    /**
     * 最后访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastAccessTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;


    public VisitorDTO(VisitorPO visitorPO) {
        super(visitorPO);
    }

}
