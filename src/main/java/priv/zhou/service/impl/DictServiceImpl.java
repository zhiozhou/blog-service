package priv.zhou.service.impl;


import org.springframework.stereotype.Service;
import priv.zhou.domain.dao.DictDataDAO;
import priv.zhou.domain.dto.DTO;
import priv.zhou.domain.dto.DictDataDTO;
import priv.zhou.domain.po.DictDataPO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IDictService;
import priv.zhou.tools.RedisUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static priv.zhou.params.CONSTANT.DICT_DATA_KEY;

/**
 * 菜单 服务层实现
 *
 * @author zhou
 * @since 2020.05.29
 */
@Service
public class DictServiceImpl implements IDictService {

    private final DictDataDAO dictDataDAO;

    public DictServiceImpl(DictDataDAO dictDataDAO) {
        this.dictDataDAO = dictDataDAO;
    }

    @Override
    public OutVO<List<DictDataDTO>> dataList(DictDataDTO dictDataDTO) {
        return OutVO.success(DTO.ofPO(listData(dictDataDTO), DictDataDTO::new));
    }

    @Override
    public OutVO<Map<String, DictDataDTO>> dataMap(DictDataDTO dictDataDTO) {
        List<DictDataPO> poList = listData(dictDataDTO);
        Map<String, DictDataDTO> map = new LinkedHashMap<>();
        for (DictDataPO po : poList) {
            map.put(po.getCode(), new DictDataDTO(po));
        }
        return OutVO.success(map);
    }

    @SuppressWarnings("unchecked")
    private List<DictDataPO> listData(DictDataDTO dictDataDTO) {
        String key = DICT_DATA_KEY + dictDataDTO.getDictKey();
        List<DictDataPO> poList = (List<DictDataPO>) RedisUtil.get(key);
        if (null == poList && null != (poList = dictDataDAO.list(dictDataDTO))) {
            RedisUtil.set(DICT_DATA_KEY, poList);
        }
        return poList;
    }

}
