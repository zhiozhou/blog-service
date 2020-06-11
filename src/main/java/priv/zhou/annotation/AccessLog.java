package priv.zhou.annotation;

import priv.zhou.aspect.LogAspect;

import java.lang.annotation.*;

/**
 * 记录访问日志
 *
 * @author zhou
 * @since 2019.11.28
 * @see LogAspect#accessLog()
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLog {
}
