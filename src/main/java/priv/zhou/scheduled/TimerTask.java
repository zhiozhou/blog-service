package priv.zhou.scheduled;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import priv.zhou.domain.dao.BlogDAO;
import priv.zhou.domain.po.BlogPO;
import priv.zhou.tools.ParseUtil;
import priv.zhou.tools.RedisUtil;

import java.util.Map;

import static priv.zhou.params.CONSTANT.BLOG_PV_KEY;

@Slf4j
@Component
public class TimerTask {

    private final BlogDAO blogDAO;

    public TimerTask(BlogDAO blogDAO) {
        this.blogDAO = blogDAO;
    }


    /**
     * 每五分钟更新访问数
     */
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    private void syncPV() {
        log.info("--- 执行同步pv");
        Map<Object, Object> map = RedisUtil.getHash(BLOG_PV_KEY);
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            blogDAO.update(new BlogPO().setId(ParseUtil.integer(entry.getKey())).setPv((Long) entry.getValue()));
        }
    }
}
