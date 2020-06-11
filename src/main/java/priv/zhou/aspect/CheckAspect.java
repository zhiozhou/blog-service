package priv.zhou.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.exception.GlobalException;
import priv.zhou.params.OutVOEnum;
import priv.zhou.tools.DistributedLock;
import priv.zhou.tools.RedisLock;
import priv.zhou.tools.RedisUtil;

import javax.servlet.http.HttpServletRequest;

import static priv.zhou.params.CONSTANT.DEFAULT_FILL;
import static priv.zhou.params.CONSTANT.REPEAT_KEY;


/**
 * 检查请求的切面组件
 *
 * @author zhou
 * @since 2019.11.14
 */
@Slf4j
@Aspect
@Component
public class CheckAspect extends BaseAspect {

    /**
     * 重复提交检查
     */
    @Order(4)
    @Before(value = "@annotation(priv.zhou.annotation.CheckRepeat)")
    public void repeat() throws Exception {
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


}
