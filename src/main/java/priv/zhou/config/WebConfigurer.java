package priv.zhou.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import priv.zhou.interceptor.AccessInterceptor;
import priv.zhou.interfaces.VisitorResolver;
import priv.zhou.tools.AppContextUtil;

import java.util.List;

/**
 * 应用全局配置
 *
 * @author zhou
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    private final AccessInterceptor interceptor;

    public WebConfigurer(AccessInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    /**
     * 支持跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
    }

    /**
     * 添加自定义的参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(AppContextUtil.getBean(VisitorResolver.class));
    }

    /**
     * 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).
                addPathPatterns("/**/**").
                excludePathPatterns("/init"); // 这些不会拦截
    }


}
