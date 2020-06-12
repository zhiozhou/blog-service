package priv.zhou.annotation;

import org.springframework.core.MethodParameter;
import priv.zhou.controller.advice.VersionAdvice;
import java.lang.annotation.*;

/**
 * 跳过版本校验
 *
 * @author zhou
 * @since 2019.11.25
 * @see VersionAdvice#supports(MethodParameter, Class)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipVersion {

}
