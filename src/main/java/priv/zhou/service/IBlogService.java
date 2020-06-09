package priv.zhou.service;

import priv.zhou.domain.Page;
import priv.zhou.domain.dto.BlogDTO;
import priv.zhou.domain.dto.BlogTypeDTO;
import priv.zhou.domain.vo.ListVO;
import priv.zhou.domain.vo.OutVO;

/**
 * 博客 服务层定义
 *
 * @author zhou
 * @since 2020.05.15
 */
public interface IBlogService {

    OutVO<BlogDTO> get(BlogDTO blogDTO);

    OutVO<BlogTypeDTO> getType(BlogTypeDTO blogTypeDTO);

    OutVO<ListVO<BlogDTO>> list(BlogDTO blogDTO, Page page);


}
