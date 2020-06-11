package priv.zhou.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import priv.zhou.params.AppProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;


@Slf4j
@SuppressWarnings("unused")
public class CookieUtil {

    private final static AppProperties appProperties = AppContextUtil.getBean(AppProperties.class);

    public static String get(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies || 0 == cookies.length) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName()) && !StringUtils.isEmpty(cookie.getValue())) {
                try {
                    return URLDecoder.decode(cookie.getValue(), AppProperties.ENC);
                } catch (UnsupportedEncodingException e) {
                    log.error("获取cookie异常: e -->{1}", e);
                    return cookie.getValue();
                }
            }
        }
        return null;

    }

    public static void save(String name, String value, HttpServletResponse response) {
        save(name, value, TimeUnit.DAYS.toSeconds(3650L), response);
    }


    public static void save(String name, String value, Long maxAge, HttpServletResponse response) {
        Cookie cookie;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value, AppProperties.ENC));
        } catch (UnsupportedEncodingException e) {
            cookie = new Cookie(name, value);
        }
        cookie.setPath("/");
        cookie.setMaxAge(maxAge < Integer.MAX_VALUE ? maxAge.intValue() : Integer.MAX_VALUE);
        cookie.setDomain(appProperties.getHost());
        response.addCookie(cookie);
    }

    public static void remove(String name, HttpServletResponse response) {
        try {
            save(name, "", 0L, response);
        } catch (Exception e) {
            log.error("移除cookie异常: e -->{1}", e);
        }
    }
}
