package priv.zhou.service;

import priv.zhou.domain.Page;
import priv.zhou.domain.dto.BlogDTO;
import priv.zhou.domain.dto.BlogTypeDTO;
import priv.zhou.domain.vo.OutVO;

/**
 *  博客 服务层定义
 *
 * @author zhou
 * @since 2020.05.15
 */
public interface IBlogService {

    OutVO get(Integer id);

    OutVO getType(String key);

    OutVO list(BlogDTO blogDTO, Page page);
}
