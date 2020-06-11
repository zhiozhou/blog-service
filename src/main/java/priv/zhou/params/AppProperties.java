package priv.zhou.params;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Andy
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    public static String ENC;

    private String name;

    private String host;

    private boolean email;

    private String adminEmail;

    private Integer accessLimit;

    private Integer cacheSecond = ((Long) TimeUnit.DAYS.toSeconds(365L)).intValue();

    public void setEnc(String enc) {
        ENC = enc;
    }
}

