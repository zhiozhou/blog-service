package priv.zhou.service.impl;


import org.springframework.stereotype.Service;
import priv.zhou.domain.dao.MenuDAO;
import priv.zhou.domain.dto.DTO;
import priv.zhou.domain.dto.MenuDTO;
import priv.zhou.domain.po.MenuPO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IMenuService;
import priv.zhou.tools.RedisUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static priv.zhou.params.CONSTANT.MENU_KEY;

/**
 * 菜单 服务层实现
 *
 * @author zhou
 * @since 2020.05.29
 */
@Service
public class MenuServiceImpl implements IMenuService {

    private final MenuDAO menuDAO;

    public MenuServiceImpl(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OutVO list() {
        List<MenuPO> poList = (List<MenuPO>) RedisUtil.get(MENU_KEY);
        if (null == poList) {
            RedisUtil.set(MENU_KEY, poList = menuDAO.list());
        }
        return OutVO.success(toTree(DTO.ofPO(poList, MenuDTO::new)));
    }

    private List<MenuDTO> toTree(List<MenuDTO> dtoList) {
        Integer rootId = 0;
        TreeMap<Integer, List<MenuDTO>> groupMap = dtoList.stream().collect(Collectors.groupingBy(MenuDTO::getParentId, TreeMap::new, Collectors.toList()));
        List<MenuDTO> outList = groupMap.get(rootId);
        for (Map.Entry<Integer, List<MenuDTO>> entry : groupMap.entrySet()) {
            if (!rootId.equals(entry.getKey())) {
                MenuDTO parent = getMenu(dtoList, entry.getKey());
                parent.setChildList(entry.getValue());
            }
        }
        return outList;
    }

    private MenuDTO getMenu(List<MenuDTO> poList, Integer id) {
        return poList.stream().filter(po -> id.equals(po.getId())).findFirst().orElse(null);
    }
}
