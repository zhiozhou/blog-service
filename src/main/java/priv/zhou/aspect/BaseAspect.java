package priv.zhou.aspect;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseAspect {

    /**
     * 获取请求
     */
    protected HttpServletRequest getRequest() throws Exception {
        return getRequestAttributes().getRequest();
    }


    /**
     * 获取请求
     */
    protected HttpServletResponse getResponse() throws Exception {
        return getRequestAttributes().getResponse();
    }


    private ServletRequestAttributes getRequestAttributes() throws Exception{
        // 1.获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            throw new Exception("attributes 为空");
        }
        return attributes;
    }
}
