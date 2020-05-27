package priv.zhou.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.domain.dto.BlogTypeDTO;
import priv.zhou.domain.po.BlogTypePO;


/**
 *  数据访问模型
 *
 * @author zhou
 * @since 2020.05.21
 */
@Mapper
@Component
public interface BlogTypeDAO{


    BlogTypePO get(BlogTypeDTO dto);
}
