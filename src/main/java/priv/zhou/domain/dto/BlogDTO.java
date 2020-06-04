package priv.zhou.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.domain.po.BlogPO;

import java.util.Date;


/**
 * 博客 数据传输模型
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
     * 内容
     */
    private String content;

    /**
     * 预览文字
     */
    private String preview;

    /**
     * 预览图
     */
    private String previewImg;

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

    /**
     * 类型
     */
    private BlogTypeDTO type;

    /**
     * 所属菜单
     */
    private MenuDTO menu;


    public BlogDTO(BlogPO blogPO) {
        super(blogPO);
        this.prev = DTO.ofPO(blogPO.getPrev(), BlogDTO::new);
        this.next = DTO.ofPO(blogPO.getNext(), BlogDTO::new);
        this.menu = DTO.ofPO(blogPO.getMenu(), MenuDTO::new);
        this.type = DTO.ofPO(blogPO.getType(), BlogTypeDTO::new);
    }

}
