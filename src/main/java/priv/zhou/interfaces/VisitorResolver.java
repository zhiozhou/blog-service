package priv.zhou.interfaces;


import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.exception.GlobalException;
import priv.zhou.service.IVisitorService;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析用户
 *
 * @author zhou
 * @since 2019.5.16
 */
@Component
public class VisitorResolver implements HandlerMethodArgumentResolver {

    private final IVisitorService visitorService;

    public VisitorResolver(IVisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(CurrentVisitor.class) != null;
    }

    @Override
    public VisitorDTO resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                      NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if (null == request) {
            throw new Exception("request 为空");
        }
        OutVO<VisitorDTO> outVO = visitorService.get(request.getParameter("token"));
        if (outVO.isFail()) {
            throw new GlobalException(outVO);
        }
        return outVO.getData();
    }

}
