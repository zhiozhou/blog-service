package priv.zhou.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.domain.dto.BlogDTO;
import priv.zhou.domain.po.BlogPO;

import java.util.List;


/**
 * 数据访问模型
 *
 * @author zhou
 * @since 2020.05.15
 */
@Mapper
@Component
public interface BlogDAO {

    Integer update(BlogPO blogPO);

    BlogPO get(BlogDTO dto);

    List<BlogPO> list(BlogDTO dto);

    BlogPO getPrev(BlogDTO dto);

    BlogPO getNext(BlogDTO dto);

}
