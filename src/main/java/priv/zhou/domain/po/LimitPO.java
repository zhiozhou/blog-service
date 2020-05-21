package priv.zhou.domain.po;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Getter
@Accessors(chain = true)
public class LimitPO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer count;

	private Date time;

	public LimitPO() {
		this.count = 0;
		resetTime();
	}

	public void add() {
		count++;
	}
	public void resetTime() {
		time = new Date();
	}

}
