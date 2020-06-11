package priv.zhou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import priv.zhou.interceptor.VisitorInterceptor;
import priv.zhou.resolver.VisitorResolver;
import priv.zhou.tools.AppContextUtil;

import java.util.List;

/**
 * 应用全局配置
 *
 * @author zhou
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    private final VisitorInterceptor visitorInterceptor;

    public WebConfigurer(VisitorInterceptor visitorInterceptor) {
        this.visitorInterceptor = visitorInterceptor;
    }

    /**
     * 支持跨域
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
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

        // todo: 之后根据需求拦截部分地址即可


        registry.addInterceptor(visitorInterceptor)
                .addPathPatterns("/**/**");

    }


}
