package priv.zhou.controller.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 用于做版本校验
 */
@ControllerAdvice
public class VersionAdvice implements ResponseBodyAdvice {

    // 这里直接返回true,表示对任何handler的responsebody都调用beforeBodyWrite方法
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    // 修改responsebody
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {


        System.out.println("aaa");
        return "changed body";
    }
}
