package priv.zhou.service;

import priv.zhou.domain.dto.MenuDTO;
import priv.zhou.domain.vo.OutVO;

import java.util.List;

/**
 * 菜单 服务层定义
 *
 * @author zhou
 * @since 2020.05.29
 */
public interface IMenuService {

    OutVO<List<MenuDTO>> list();

}
