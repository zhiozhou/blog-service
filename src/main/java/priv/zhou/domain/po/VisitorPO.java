package priv.zhou.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.Date;

/**
 * 访客 数据持久化模型
 *
 * @author zhou
 * @since 2020.06.08
 */
@Getter
@Setter
@Accessors(chain = true)
public class VisitorPO implements Serializable{


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
	private Date lastAccessTime;

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
