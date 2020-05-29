package priv.zhou.service;

import priv.zhou.domain.dto.DictDataDTO;
import priv.zhou.domain.vo.OutVO;

/**
 *  字典 服务层定义
 *
 * @author zhou
 * @since 2020.04.17
 */
public interface IDictService {

    OutVO listData(DictDataDTO dictDataDTO);

}
