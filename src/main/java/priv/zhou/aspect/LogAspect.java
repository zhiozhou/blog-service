package priv.zhou.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import priv.zhou.async.Treadmill;
import priv.zhou.domain.dto.AccessLogDTO;
import priv.zhou.tools.CookieUtil;
import priv.zhou.tools.HttpUtil;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static priv.zhou.misc.CONSTANT.TOKEN_KEY;


/**
 * 日志的切面组件
 *
 * @author zhou
 * @since 2019.11.14
 */
@Slf4j
@Aspect
@Component
@DependsOn("treadmill")
public class LogAspect extends BaseAspect {

    private final Treadmill treadmill;

    public LogAspect(Treadmill treadmill) {
        this.treadmill = treadmill;
    }

    @Pointcut("execution(public * priv.zhou.controller.*.*(..))")
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
     * 记录访问日志
     */
    @Order(3)
    @Before(value = "@annotation(priv.zhou.annotation.AccessLog)")
    public void accessLog() throws Exception {
        HttpServletRequest request = getRequest();
        String token = CookieUtil.get(TOKEN_KEY, request);
        if (null == token) {
            token = (String) request.getAttribute(TOKEN_KEY);
        }
        AccessLogDTO accessLogDTO = new AccessLogDTO()
                .setToken(token)
                .setHost(HttpUtil.getIpAddress(request))
                .setUserAgent(HttpUtil.getUserAgent(request))
                .setApi(request.getRequestURI())
                .setParam(HttpUtil.getParams(request))
                .setGmtCreate(new Date());
        treadmill.accessLog(accessLogDTO);
    }



}
