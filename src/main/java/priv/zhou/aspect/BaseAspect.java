package priv.zhou.aspect;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseAspect {

    /**
     * 获取请求
     */
    protected HttpServletRequest getRequest() throws Exception {
        // 1.获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            throw new Exception("attributes 为空");
        }
        return attributes.getRequest();
    }
}
