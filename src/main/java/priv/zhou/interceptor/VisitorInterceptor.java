package priv.zhou.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IVisitorService;
import priv.zhou.tools.CookieUtil;
import priv.zhou.tools.HttpUtil;
import priv.zhou.tools.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static priv.zhou.params.CONSTANT.TOKEN_KEY;
import static priv.zhou.tools.TokenUtil.*;


/**
 * 访客拦截器
 * 校验 token ，校验失败会重新生成
 *
 * @author zhou
 * @since 2020.6.9
 */
@Component
public class VisitorInterceptor implements HandlerInterceptor {

    private final IVisitorService visitorService;

    public VisitorInterceptor(IVisitorService visitorService) {
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
            tokenMap.put(MENU_VERSION, 0);
            tokenMap.put(SNS_VERSION, 0);

            token = TokenUtil.build(tokenMap);
            request.setAttribute(TOKEN_KEY, token);

            // 方便 next 服务器可复制响应头
            response.addHeader("Access-Control-Expose-Headers","Set-Cookie");
            CookieUtil.save(TOKEN_KEY, token, response);

        }
        request.setAttribute("mmmmm", token);
        response.addHeader("Access-Control-Expose-Headers","mmmmm");
        return true;
    }
}