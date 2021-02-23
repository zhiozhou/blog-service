package priv.zhou.tools;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Maps;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("unused")
public class TokenUtil {

    /**
     * 游客id
     */
    public final static String VISITOR_ID = "visitorId";

    /**
     * 菜单版本
     */
    public final static String MENU_VERSION = "menuVersion";

    /**
     * sns版本
     */
    public final static String SNS_VERSION = "snsVersion";


    public static String build(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key,value);
        return build(map);
    }


    public static String build(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKey()).compact();
    }

    /**
     * 解析Token,同事验证Token
     * 解析失败时返回null
     */
    public static Map<String, Object> parse(String jwt) {
        try {
            return StringUtils.isBlank(jwt) ? null : Jwts.parser().setSigningKey(getKey()).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer parseId(String jwt) {
        Map<String, Object> map = parse(jwt);
        if (!verify(map)) {
            return null;
        }
        return (Integer) map.get(VISITOR_ID);
    }

    public static boolean verify(String jwt) {
        Map<String, Object> map = parse(jwt);
        return verify(map);
    }

    public static boolean verify(Map<String, Object> tokenMap) {
        return null != tokenMap
                && null != tokenMap.get(VISITOR_ID)
                && null != tokenMap.get(MENU_VERSION)
                && null != tokenMap.get(SNS_VERSION);
    }


    private static Key getKey() {
        return new SecretKeySpec(DatatypeConverter.parseBase64Binary("zhousb-blog"), SignatureAlgorithm.HS256.getJcaName());
    }

}
