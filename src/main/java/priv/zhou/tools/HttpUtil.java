package priv.zhou.tools;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.misc.AppProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;

@Slf4j
@SuppressWarnings("unused")
public class HttpUtil {

    private final static String[] IP_HEADERS = {"X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};

    private final static String[] PHONE_AGENTS = new String[]{"iphone", "ios", "android", "windows phone"};


    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    /**
     * 获取Ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!emptyIp(ip)) {
            //多次反向代理后会有多个ip值,第0位ip才是真实ip
            return ip.split(",")[0];
        }

        for (int i = 0; i < IP_HEADERS.length && emptyIp(ip); i++) {
            ip = request.getHeader(IP_HEADERS[i]);
        }
        return emptyIp(ip) ? request.getRemoteAddr() : ip;
    }

    private static boolean emptyIp(String ip) {
        return StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip);
    }


    /**
     * 是否为手机访问
     */
    public static boolean phoneAccess(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        if (StringUtils.isNotBlank(userAgent)) {
            userAgent = userAgent.toLowerCase();
            for (String agent : PHONE_AGENTS) {
                if (userAgent.contains(agent)) return true;
            }
        }
        return false;
    }

    /**
     * 返回错误信息
     */
    public static void out(HttpServletResponse response, OutVO<?> outVO) {
        response.setCharacterEncoding(AppProperties.ENC);
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(outVO);
            out.flush();
        } catch (Exception e) {
            log.error("响应异常", e);
        }
    }


    public static String getBody(HttpServletRequest request) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bodyBuilder.append(line);
            }
        }
        return bodyBuilder.toString();
    }


    public static JSONObject getParams(HttpServletRequest request) {
        JSONObject params = new JSONObject();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramKey = enumeration.nextElement();
            params.put(paramKey, request.getParameter(paramKey));
        }
        return params;
    }
}

