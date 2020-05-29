package priv.zhou.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.domain.po.BlogTypePO;

import java.util.Date;


/**
 * 博客类型 数据传输模型
 *
 * @author zhou
 * @since 2020.05.21
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogTypeDTO extends DTO<BlogTypePO> {


    /**
     * 
     */
    private Integer id;


    /**
     * 标识
     */
    private String key;


    /**
     * 名称
     */
    private String name;


    /**
     * 标题
     */
    private String title;


    /**
     * 描述
     */
    private String desc;

    /**
     * 背景
     */
    private String bg;

    /**
     * 备注
     */
    private String remark;

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


    public BlogTypeDTO(BlogTypePO blogTypePO) {
        super(blogTypePO);
    }

}
