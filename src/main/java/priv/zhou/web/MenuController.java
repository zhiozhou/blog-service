package priv.zhou.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.domain.dto.MenuDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IMenuService;

import java.util.List;


/**
 * 控制层
 *
 * @author zhou
 * @since 2020.05.15
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final IMenuService menuService;

    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping("/list")
    public OutVO<List<MenuDTO>> list() {
        return menuService.list();
    }
}
