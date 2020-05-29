package priv.zhou.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.domain.po.MenuPO;

import java.util.List;


/**
 *  菜单 数据访问层定义
 *
 * @author zhou
 * @since 2020.05.29
 */
@Mapper
@Component
public interface MenuDAO {

    List<MenuPO> list();
}
