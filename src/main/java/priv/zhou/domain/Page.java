package priv.zhou.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhou
 */
@Getter
@Setter
public class Page {

	private Integer page = 1;

	private Integer limit = 10;

	private boolean count = true;

}
