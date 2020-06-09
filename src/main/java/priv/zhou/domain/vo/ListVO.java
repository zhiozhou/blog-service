package priv.zhou.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 列表分页 通用返回模型
 * @author zhou
 */
@Getter
@Setter
@Accessors(chain = true)
public class ListVO<DTO> {

	private List<DTO> list;

	private boolean hasMore;


}
