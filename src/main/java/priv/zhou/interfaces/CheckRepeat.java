package priv.zhou.interfaces;

import priv.zhou.aspect.WebAspect;

import java.lang.annotation.*;

/**
 * 检查是否重复提交
 * @author zhou
 * @since 2019.11.28
 * @see WebAspect#checkRepeat()
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckRepeat {

}
