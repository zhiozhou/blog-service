package priv.zhou.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.domain.po.BlogPO;

import java.util.Date;


/**
 * 数据传输模型
 *
 * @author zhou
 * @since 2020.05.15
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogDTO extends DTO<BlogPO> {

    /**
     *
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;


    /**
     * 文章类型
     */
    private String type;

    private String typeName;

    /**
     * 内容
     */
    private String content;

    /**
     * 预览
     */
    private String preview;

    /**
     * 备注
     */
    private String remark;

    /**
     * 页面访问量
     */
    private Long pv;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;


    private BlogDTO prev;


    private BlogDTO next;


    public BlogDTO(BlogPO blogPO) {
        super(blogPO);
        this.prev = null == blogPO.getPrev() ? null : new BlogDTO(blogPO.getPrev());
        this.next = null == blogPO.getNext() ? null : new BlogDTO(blogPO.getNext());
    }

}
