package priv.zhou.interfaces;

import priv.zhou.aspect.WebAspect;

import java.lang.annotation.*;

/**
 * 记录访问日志
 *
 * @author zhou
 * @since 2019.11.28
 * @see WebAspect#accessLog()
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLog {
}
