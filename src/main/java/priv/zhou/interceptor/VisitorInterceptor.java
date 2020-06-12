package priv.zhou.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import priv.zhou.controller.DictController;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IDictService;
import priv.zhou.service.IMenuService;
import priv.zhou.service.IVisitorService;
import priv.zhou.tools.CookieUtil;
import priv.zhou.tools.HttpUtil;
import priv.zhou.tools.ParseUtil;
import priv.zhou.tools.TokenUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static priv.zhou.misc.CONSTANT.SSR_HEADER_KEY;
import static priv.zhou.misc.CONSTANT.TOKEN_KEY;
import static priv.zhou.tools.TokenUtil.*;


/**
 * 访客拦截器
 * 校验 token ，校验失败会重新生成
 *
 * @author zhou
 * @since 2020.6.9
 */
@Slf4j
@Component
public class VisitorInterceptor implements HandlerInterceptor {

    private final IMenuService menuService;

    private final IDictService dictService;

    private final IVisitorService visitorService;

    public VisitorInterceptor(IMenuService menuService, IDictService dictService, IVisitorService visitorService) {
        this.menuService = menuService;
        this.dictService = dictService;
        this.visitorService = visitorService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = CookieUtil.get(TOKEN_KEY, request);
        Map<String, Object> tokenMap = TokenUtil.parse(token);
        if (!TokenUtil.verify(tokenMap)) {
            tokenMap = new HashMap<>();
            OutVO<VisitorDTO> createVO = visitorService.create();
            if (createVO.isFail()) {
                HttpUtil.out(response, createVO);
                return false;
            }
            tokenMap.put(VISITOR_ID, createVO.getData().getId());
            tokenMap.put(MENU_VERSION, menuService.latestVersion());
            tokenMap.put(SNS_VERSION, dictService.latestVersion(DictController.SNS_KEY));

            // 此次请求如果需要可以在attribute中取
            request.setAttribute(TOKEN_KEY, token = TokenUtil.build(tokenMap));
            Cookie cookie = CookieUtil.create(TOKEN_KEY, token);
            if (ParseUtil.bool(request.getHeader(SSR_HEADER_KEY))) {
                // token暴露给服务器端
                response.addHeader(TOKEN_KEY, CookieUtil.toHeader(cookie));
            } else {
                response.addCookie(cookie);
            }
        }
        return true;
    }
}