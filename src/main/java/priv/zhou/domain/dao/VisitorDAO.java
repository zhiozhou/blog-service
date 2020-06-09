package priv.zhou.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.po.VisitorPO;



/**
 * 访客 数据访问模型
 *
 * @author zhou
 * @since 2020.06.08
 */
@Mapper
@Component
public interface VisitorDAO{

    Integer save(VisitorPO visitorPO);

    Integer update(VisitorPO visitorPO);

    VisitorPO get(VisitorDTO visitorDTO);
}
