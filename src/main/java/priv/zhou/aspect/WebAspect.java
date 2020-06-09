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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import priv.zhou.async.Treadmill;
import priv.zhou.domain.dto.AccessLogDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.exception.GlobalException;
import priv.zhou.params.OutVOEnum;
import priv.zhou.tools.*;

import javax.servlet.http.HttpServletRequest;

import static priv.zhou.params.CONSTANT.DEFAULT_FILL;
import static priv.zhou.params.CONSTANT.REPEAT_KEY;


/**
 * 校验&日志aop
 *
 * @author zhou
 * @since 2019.11.14
 */
@Slf4j
@Aspect
@Component
@DependsOn("treadmill")
public class WebAspect {

    private final Treadmill treadmill;

    public WebAspect(Treadmill treadmill) {
        this.treadmill = treadmill;
    }

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
     * 记录访问日志
     */
    @Order(3)
    @Before(value = "@annotation(priv.zhou.interfaces.AccessLog)")
    public void accessLog() throws Exception {
        HttpServletRequest request = getRequest();
        AccessLogDTO accessLogDTO = new AccessLogDTO()
                .setVisitorId(TokenUtil.parseId(request.getParameter("token")))
                .setHost(HttpUtil.getIpAddress(request))
                .setUserAgent(HttpUtil.getUserAgent(request))
                .setApi(request.getRequestURI())
                .setParam(HttpUtil.getParams(request));
        treadmill.accessLog(accessLogDTO);
    }

    /**
     * 重复提交检查
     */
    @Order(4)
    @Before(value = "@annotation(priv.zhou.interfaces.CheckRepeat)")
    public void checkRepeat() throws Exception {
        HttpServletRequest request = getRequest();
        String paramStr = JSON.toJSONString(request.getParameterMap()),
                key = REPEAT_KEY + request.getRequestURI() + paramStr;
        try (DistributedLock lock = RedisLock.build(key)) {
            if (!lock.acquire()) {
                throw new GlobalException(OutVO.fail(OutVOEnum.LATER_RETRY));
            } else if (null != RedisUtil.get(key)) {
                throw new GlobalException(OutVO.fail(OutVOEnum.OFTEN_OPERATION));
            }
            RedisUtil.set(key, DEFAULT_FILL, 5);
        }
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
