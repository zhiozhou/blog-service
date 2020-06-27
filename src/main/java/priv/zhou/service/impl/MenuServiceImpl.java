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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static priv.zhou.misc.CONSTANT.MENU_KEY;
import static priv.zhou.misc.CONSTANT.MENU_MODIFIED_KEY;

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
    public OutVO<List<MenuDTO>> list() {
        List<MenuPO> poList = (List<MenuPO>) RedisUtil.get(MENU_KEY);
        if (null == poList && null != (poList = menuDAO.list())) {
            RedisUtil.set(MENU_KEY, poList);
        }
        return OutVO.success(toTree(DTO.ofPO(poList, MenuDTO::new)));
    }

    @Override
    public Long latestVersion() {
        Long value = (Long) RedisUtil.get(MENU_MODIFIED_KEY);
        if (null == value) {
            RedisUtil.set(MENU_MODIFIED_KEY, value = menuDAO.sumModifiedStamp());
        }
        return value;
    }


    private List<MenuDTO> toTree(List<MenuDTO> dtoList) {
        if (dtoList.isEmpty()) {
            return dtoList;
        }
        Integer rootId = 0;
        TreeMap<Integer, List<MenuDTO>> groupMap = dtoList.stream().collect(Collectors.groupingBy(MenuDTO::getParentId, TreeMap::new, Collectors.toList()));
        List<MenuDTO> outList = groupMap.get(rootId);
        for (Map.Entry<Integer, List<MenuDTO>> entry : groupMap.entrySet()) {
            if (!rootId.equals(entry.getKey())) {
                MenuDTO parent = getMenu(dtoList, entry.getKey());
                if (null != parent) {
                    parent.setChildList(entry.getValue());
                }
            }
        }
        return outList;
    }

    private MenuDTO getMenu(List<MenuDTO> poList, Integer id) {
        return poList.stream().filter(po -> id.equals(po.getId())).findFirst().orElse(null);
    }
}
