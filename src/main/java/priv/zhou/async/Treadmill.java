package priv.zhou.async;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import priv.zhou.domain.dao.AccessLogDAO;
import priv.zhou.domain.dto.AccessLogDTO;
import priv.zhou.domain.po.AccessLogPO;
import priv.zhou.config.AsyncConfig;


/**
 * 跑步机
 *
 * 用于处理各种异步动作
 *
 * @see AsyncConfig
 */
@Slf4j
@Component
public class Treadmill {

    private final AccessLogDAO accessLogDAO;

    public Treadmill(AccessLogDAO accessLogDAO) {
        this.accessLogDAO = accessLogDAO;
    }

    @Async("executor")
    public void accessLog(AccessLogDTO accessLogDTO) {
        UserAgent userAgent = UserAgent.parseUserAgentString(accessLogDTO.getUserAgent());
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        AccessLogPO accessLogPO = accessLogDTO.toPO()
                .setMfrs(operatingSystem.getManufacturer().toString())
                .setDevice(operatingSystem.getDeviceType().toString())
                .setOs(operatingSystem.getName())
                .setBrowser(userAgent.getBrowser().getName());
        if (accessLogDAO.save(accessLogPO) < 1) {
            log.error("访问日志记录失败");
        }
    }

}
