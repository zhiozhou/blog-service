package priv.zhou.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.domain.dto.DictDataDTO;
import priv.zhou.domain.po.DictDataPO;

import java.util.List;


/**
 * 字典 数据访问模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Mapper
@Component
public interface DictDataDAO {

    List<DictDataPO> list(DictDataDTO dictDataDTO);

}
