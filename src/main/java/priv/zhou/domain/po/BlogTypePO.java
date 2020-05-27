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
 * @since 2020.05.21
 */
@Getter
@Setter
@Accessors(chain = true)
public class BlogTypePO implements Serializable{

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
	 * 状态
	 */
	private Integer state;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
