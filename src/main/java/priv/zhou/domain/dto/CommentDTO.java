package priv.zhou.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.domain.po.CommentPO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;


/**
 * 评论 数据传输模型
 *
 * @author zhou
 * @since 2020.06.16
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO extends DTO<CommentPO> {


    /**
     *
     */
    private Integer id;

    /**
     * 博客id
     */
    @NotNull(message = "博客标识不可为空")
    private Integer blogId;

    /**
     * 主题id
     */
    private Integer topicId;

    /**
     * 回复的id
     */
    private Integer repliedId;

    /**
     * 目标访客
     */
    private VisitorDTO toVisitor;

    /**
     * 访客
     */
    private VisitorDTO fromVisitor;

    /**
     * 内容
     */
    @NotEmpty(message = "内容不可为空")
    @Pattern(regexp = "^.{3,256}$", message = "内容非法")
    @Pattern(regexp = ".*[\u4e00-\u9fa5].*", message = "Can you speak chinese?")
    private String content;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 主题回复总数
     */
    private Integer replyCount;

    /**
     * 回复列表
     */
    private List<CommentDTO> replyList;

    public CommentDTO(CommentPO commentPO) {
        super(commentPO);
        this.toVisitor = DTO.ofPO(commentPO.getToVisitor(), VisitorDTO::new);
        this.fromVisitor = DTO.ofPO(commentPO.getFromVisitor(), VisitorDTO::new);
        this.replyList = DTO.ofPO(commentPO.getReplyList(), CommentDTO::new);
    }

    @Override
    public CommentPO toPO() {
        CommentPO commentPO = super.toPO()
                .setFromVisitor(fromVisitor.toPO());
        if (null != toVisitor) {
            commentPO.setToVisitor(toVisitor.toPO());
        }
        return commentPO;
    }
}
