package priv.zhou.annotation;

import priv.zhou.aspect.CheckAspect;
import java.lang.annotation.*;

/**
 * 检查是否重复提交
 * @author zhou
 * @since 2019.11.28
 * @see CheckAspect#repeat()
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckRepeat {
}
