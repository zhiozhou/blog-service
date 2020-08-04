package priv.zhou.aspect;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.exception.GlobalException;
import priv.zhou.misc.OutVOEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseAspect {

    /**
     * 获取请求
     */
    protected HttpServletRequest getRequest() throws GlobalException {
        return getRequestAttributes().getRequest();
    }


    /**
     * 获取请求
     */
    protected HttpServletResponse getResponse() throws GlobalException {
        return getRequestAttributes().getResponse();
    }


    private ServletRequestAttributes getRequestAttributes() throws GlobalException {
        // 1.获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            throw new GlobalException(OutVO.fail(OutVOEnum.LATER_RETRY));
        }
        return attributes;
    }
}
