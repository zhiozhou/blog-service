package priv.zhou.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *  数据持久化模型
 *
 * @author zhou
 * @since 2020.05.15
 */
@Getter
@Setter
@Accessors(chain = true)
public class BlogPO implements Serializable{

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

	private String typeLabel;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 预览
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
	 * 博客状态
	 */
	private Integer state;

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


	private BlogPO prev;

	private BlogPO next;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
