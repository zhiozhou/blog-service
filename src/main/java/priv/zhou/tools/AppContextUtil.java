package priv.zhou.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author zhou
 * @since 2019.03.11
 */
@Component
public class AppContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        AppContextUtil.context = context;
    }


    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return context.getBean(beanName, requiredType);
    }

    public static String getMessage(String key) {
        return context.getMessage(key, null, Locale.getDefault());
    }


    public static String getProfiles() {
        String[] profiles = context.getEnvironment().getActiveProfiles();
        if (0 == profiles.length) {
            profiles = context.getEnvironment().getDefaultProfiles();
        }
        return profiles[0];
    }
}
