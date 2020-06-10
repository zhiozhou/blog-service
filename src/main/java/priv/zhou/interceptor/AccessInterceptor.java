package priv.zhou.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.params.OutVOEnum;
import priv.zhou.tools.HttpUtil;
import priv.zhou.tools.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 访问拦截器
 *
 * @author zhou
 * @since 2020.6.9
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!TokenUtil.verify(request.getParameter("token"))) {
            HttpUtil.out(response, OutVO.fail(OutVOEnum.NEED_INIT));
            return false;
        }
        return true;
    }

}