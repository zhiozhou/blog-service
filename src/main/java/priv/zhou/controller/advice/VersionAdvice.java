package priv.zhou.controller.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.params.OutVOEnum;
import priv.zhou.service.IDictService;
import priv.zhou.service.IMenuService;
import priv.zhou.tools.CookieUtil;
import priv.zhou.tools.ParseUtil;
import priv.zhou.tools.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

import static priv.zhou.params.CONSTANT.SNS_DICT_KEY;
import static priv.zhou.params.CONSTANT.TOKEN_KEY;
import static priv.zhou.tools.TokenUtil.MENU_VERSION;
import static priv.zhou.tools.TokenUtil.SNS_VERSION;


/**
 * 用于做版本校验
 */
@ControllerAdvice
public class VersionAdvice implements ResponseBodyAdvice<OutVO<?>> {

    private final IMenuService menuService;

    private final IDictService dictService;

    public VersionAdvice(IMenuService menuService, IDictService dictService) {
        this.menuService = menuService;
        this.dictService = dictService;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        Method method = methodParameter.getMethod();
        return null != method && method.getReturnType().equals(OutVO.class);
    }


    @Override
    public OutVO<?> beforeBodyWrite(OutVO<?> outVO, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest serverRequest, ServerHttpResponse serverResponse) {
        HttpServletRequest request = ((ServletServerHttpRequest) serverRequest).getServletRequest();
        Long menuLatest = menuService.latestVersion(), snsLatest = dictService.latestVersion(SNS_DICT_KEY);
        Map<String, Object> tokenMap = TokenUtil.parse(CookieUtil.get(TOKEN_KEY, request));
        if (null == outVO || outVO.isFail() || null == tokenMap) {
            return outVO;
        } else if (menuLatest > ParseUtil.longer(tokenMap.get(MENU_VERSION)) || snsLatest > ParseUtil.longer(tokenMap.get(SNS_VERSION))) {
            outVO.setEnum(OutVOEnum.VERSION_DEPRECATED);
        }
        return outVO;
    }


}
