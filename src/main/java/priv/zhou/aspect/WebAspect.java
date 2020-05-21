package priv.zhou.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import priv.zhou.tools.HttpUtil;

import javax.servlet.http.HttpServletRequest;


/**
 * 校验&日志aop
 *
 * @author zhou
 * @since 2019.11.14
 */
@Slf4j
@Aspect
@Component
public class WebAspect {


    @Pointcut("execution(public * priv.zhou.web.*.*(..))")
    public void webCut() {
    }


    /**
     * 请求日志
     */
    @Order(0)
    @Before(value = "webCut()")
    public void beforeLog() throws Exception {
        HttpServletRequest request = getRequest();
        log.info("调用 {} 接口,请求参数 -->{}", request.getRequestURI(), HttpUtil.getParams(request));
    }


    /**
     * 返回日志
     */
    @AfterReturning(returning = "result", pointcut = "webCut()")
    public void AfterExec(Object result) throws Exception {
        log.info("退出 {} 接口,返回报文 -->{}\n", getRequest().getRequestURI(), JSON.toJSONString(result));
    }


    /**
     * 获取请求
     */
    private HttpServletRequest getRequest() throws Exception {
        // 1.获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new Exception("attributes 为空");
        }
        return attributes.getRequest();
    }

}
