package priv.zhou.params;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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

	private Integer accessLimit;

	private String adminEmail;

	private Integer cacheSecond = 60 * 60 * 24 * 30 * 12; //缓存一年

	public void setEnc(String enc) {
		ENC = enc;
	}
}

