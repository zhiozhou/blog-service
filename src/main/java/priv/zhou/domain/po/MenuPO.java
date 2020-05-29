package priv.zhou.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单 数据传输模型
 *
 * @author zhou
 * @since 2020.05.29
 */
@Getter
@Setter
@Accessors(chain = true)
public class MenuPO implements Serializable {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 父级id
	 */
	private Integer parentId;

	/**
	 * 权限标识
	 */
	private String key;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 类型 0 目录 1 菜单 2按钮
	 */
	private Integer type;

	/**
	 * 状态 0 正常 11隐藏
	 */
	private Integer state;

	/**
	 * 排序
	 */
	private Integer sort;
}
