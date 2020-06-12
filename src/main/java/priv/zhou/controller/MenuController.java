package priv.zhou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.domain.dto.MenuDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IMenuService;
import priv.zhou.tools.CookieUtil;
import priv.zhou.tools.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static priv.zhou.params.CONSTANT.TOKEN_KEY;
import static priv.zhou.tools.TokenUtil.MENU_VERSION;


/**
 * 菜单 控制层
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
    public OutVO<List<MenuDTO>> list(HttpServletRequest request, HttpServletResponse response) {
        OutVO<List<MenuDTO>> outVO = menuService.list();
        Map<String, Object> tokenMap = TokenUtil.parse(CookieUtil.get(TOKEN_KEY, request));
        if (outVO.isFail() || null == tokenMap) {
            return outVO;
        }
        tokenMap.put(MENU_VERSION, menuService.latestVersion());
        response.addCookie(CookieUtil.create(TOKEN_KEY, TokenUtil.build(tokenMap)));
        return outVO;
    }
}
