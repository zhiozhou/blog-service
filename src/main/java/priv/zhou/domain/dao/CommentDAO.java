package priv.zhou.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import priv.zhou.domain.Page;
import priv.zhou.domain.dto.CommentDTO;
import priv.zhou.domain.po.CommentPO;

import java.util.List;


/**
 * 评论 数据访问模型
 *
 * @author zhou
 * @since 2020.06.16
 */
@Mapper
@Component
public interface CommentDAO {


    List<CommentPO> list(@Param("dto") CommentDTO commentDTO, @Param("page") Page page);

    Integer count(CommentDTO commentDTO);
}
