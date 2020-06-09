package priv.zhou.service;

import priv.zhou.domain.dto.DictDataDTO;
import priv.zhou.domain.vo.OutVO;

import java.util.List;
import java.util.Map;

/**
 * 字典 服务层定义
 *
 * @author zhou
 * @since 2020.04.17
 */
public interface IDictService {

    OutVO<List<DictDataDTO>>  dataList(DictDataDTO dictDataDTO);

    OutVO<Map<String, DictDataDTO>> dataMap(DictDataDTO dictDataDTO);

}
