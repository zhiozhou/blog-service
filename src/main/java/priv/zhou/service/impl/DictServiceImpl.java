package priv.zhou.service.impl;


import org.springframework.stereotype.Service;
import priv.zhou.domain.dao.DictDataDAO;
import priv.zhou.domain.dto.DTO;
import priv.zhou.domain.dto.DictDataDTO;
import priv.zhou.domain.po.DictDataPO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IDictService;
import priv.zhou.tools.RedisUtil;

import java.util.List;

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
    @SuppressWarnings("unchecked")
    public OutVO listData(DictDataDTO dictDataDTO) {
        String key = DICT_DATA_KEY + dictDataDTO.getDictKey();
        List<DictDataPO> poList = (List<DictDataPO>) RedisUtil.get(key);
        if (null == poList) {
            RedisUtil.set(DICT_DATA_KEY, poList = dictDataDAO.list(dictDataDTO));
        }
        return OutVO.success(DTO.ofPO(poList, DictDataDTO::new));
    }

}
