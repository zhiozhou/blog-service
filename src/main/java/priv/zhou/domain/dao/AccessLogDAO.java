package priv.zhou.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.domain.po.AccessLogPO;


/**
 * 访问日志 数据访问模型
 *
 * @author zhou
 * @since 2020.06.08
 */
@Mapper
@Component
public interface AccessLogDAO {

    Integer save(AccessLogPO accessLogPO);
}
