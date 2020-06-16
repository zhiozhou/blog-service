package priv.zhou.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author zhou
 */
@Getter
@Setter
@Accessors(chain = true)
public class Page {

	private Integer page = 1;

	/**
	 * 用于不走pageHelper
	 */
	private Integer offset;

	private Integer limit = 10;

	private boolean count = true;

}
