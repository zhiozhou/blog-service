package priv.zhou.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import priv.zhou.resolver.VisitorResolver;

import java.lang.annotation.*;

/**
 * userNo转换为userDTO
 *
 * @author zhou
 * @since 2019.11.25
 * @see VisitorResolver#resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentVisitor {

}
