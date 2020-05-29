package priv.zhou.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 字典数据 数据持久化模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class DictDataPO implements Serializable{

	/**
	 * 标识码
	 */
	private String code;

	/**
	 * 标签
	 */
	private String label;

	/**
	 * 字典标识
	 */
	private String dictKey;

}
