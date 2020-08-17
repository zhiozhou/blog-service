package priv.zhou.aspect;

import com.alibaba.fastjson.JSON;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import priv.zhou.async.Treadmill;
import priv.zhou.controller.DictController;
import priv.zhou.domain.dto.AccessLogDTO;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.exception.GlobalException;
import priv.zhou.misc.OutVOEnum;
import priv.zhou.service.IDictService;
import priv.zhou.service.IMenuService;
import priv.zhou.service.IVisitorService;
import priv.zhou.tools.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static priv.zhou.misc.CONSTANT.*;
import static priv.zhou.tools.TokenUtil.*;


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
public class WebAspect extends BaseAspect {

    private final Treadmill treadmill;

    private final IMenuService menuService;

    private final IDictService dictService;

    private final IVisitorService visitorService;

    public WebAspect(Treadmill treadmill, IMenuService menuService, IDictService dictService, IVisitorService visitorService) {
        this.treadmill = treadmill;
        this.menuService = menuService;
        this.dictService = dictService;
        this.visitorService = visitorService;
    }

    @Pointcut("execution(public * priv.zhou.controller.*.*(..))")
    public void webCut() {
    }

    /**
     * 重复提交检查
     */
    @Order(0)
    @Before(value = "@annotation(priv.zhou.annotation.CheckRepeat)")
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
     * 请求日志
     */
    @Order(1)
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
     * 检查Token
     */
    @Order(2)
    @Before(value = "webCut()")
    public void checkToken() throws GlobalException {
        HttpServletRequest request = getRequest();

        String token = CookieUtil.get(TOKEN_KEY, request);
        Map<String, Object> tokenMap = TokenUtil.parse(token);
        if (!TokenUtil.verify(tokenMap)) {

            // 1.校验请求
            String requestApi = request.getRequestURI(),
                    userAgent = HttpUtil.getUserAgent(request);
            if ("unknown".equalsIgnoreCase(UserAgent.parseUserAgentString(userAgent).getBrowser().toString())) {
                return;
            }

            // 2.创建访客
            OutVO<VisitorDTO> createVO = visitorService.create();
            if (createVO.isFail()) {
                throw new GlobalException(createVO);
            }

            // 3.生成Token
            tokenMap = new HashMap<>();
            tokenMap.put(VISITOR_ID, createVO.getData().getId());
            tokenMap.put(MENU_VERSION, menuService.latestVersion());
            tokenMap.put(SNS_VERSION, dictService.latestVersion(DictController.SNS_KEY));
            request.setAttribute(TOKEN_KEY, token = TokenUtil.build(tokenMap)); // 此次请求如果需要可以在attribute中取

            // 4.记录日志
            AccessLogDTO accessLogDTO = new AccessLogDTO()
                    .setToken(token)
                    .setApi(requestApi)
                    .setUserAgent(userAgent)
                    .setParam(HttpUtil.getParams(request))
                    .setHost(HttpUtil.getIpAddress(request))
                    .setGmtCreate(new Date());
            treadmill.accessLog(accessLogDTO);

            // 5.存储Token
            HttpServletResponse response = getResponse();
            Cookie cookie = CookieUtil.create(TOKEN_KEY, token);
            if (ParseUtil.bool(request.getHeader(SSR_HEADER_KEY))) {
                // token暴露给服务器端
                response.addHeader(TOKEN_KEY, CookieUtil.toHeader(cookie));
            } else {
                response.addCookie(cookie);
            }
        }
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
