package priv.zhou.exception;

import priv.zhou.domain.vo.OutVO;
import lombok.Getter;

/**
 * 活动错误异常
 * 用于活动实体解析时进行抛出
 */
@Getter
public class GlobalException extends Exception {

	/**
	 * 包含错误信息的vo对象
	 */
	private final OutVO outVO;

	public GlobalException(OutVO outVO) {
		this.outVO = outVO;
	}
}
