package priv.zhou.tools;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;


@SuppressWarnings("unused")
public class TokenUtil {

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

    public static Integer parseId(String jwt) throws Exception {
        Map<String, Object> map = parse(jwt);
        if (!verify(map)) {
            return null;
        }
        return (Integer) map.get("id");
    }

    public static boolean verify(String jwt) {
        Map<String, Object> map = parse(jwt);
        return verify(map);
    }

    public static boolean verify(Map<String, Object> tokenMap) {
        return null != tokenMap && !tokenMap.isEmpty() && null != tokenMap.get("id") && null != tokenMap.get("version");
    }


    private static Key getKey() {
        return new SecretKeySpec(DatatypeConverter.parseBase64Binary("zhousb"), SignatureAlgorithm.HS256.getJcaName());
    }


}
