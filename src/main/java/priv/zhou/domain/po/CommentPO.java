package priv.zhou.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论 数据持久化模型
 *
 * @author zhou
 * @since 2020.06.16
 */
@Getter
@Setter
@Accessors(chain = true)
public class CommentPO implements Serializable{

	/**
	 * 
	 */
	private Integer id;

	/**
	 * 博客id
	 */
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
	private VisitorPO toVisitor;

	/**
	 * 访客
	 */
	private VisitorPO fromVisitor;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 状态
	 */
	private String state;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	/**
	 * 主题回复总数
	 */
	private Integer replyCount;

	/**
	 * 回复列表
	 */
	private List<CommentPO> replyList;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
