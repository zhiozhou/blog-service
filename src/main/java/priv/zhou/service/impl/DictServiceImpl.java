package priv.zhou.service.impl;


import org.springframework.stereotype.Service;
import priv.zhou.domain.dao.DictDataDAO;
import priv.zhou.domain.dto.DTO;
import priv.zhou.domain.dto.DictDataDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IDictService;
import priv.zhou.tools.RedisUtil;

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
    public OutVO listData(DictDataDTO dictDataDTO) {
        String key = DICT_DATA_KEY + dictDataDTO.getDictKey();
        Object dtoList = RedisUtil.get(key);
        if (null == dtoList) {
            RedisUtil.set(DICT_DATA_KEY, dtoList = DTO.ofPO(dictDataDAO.list(dictDataDTO), DictDataDTO::new));
        }
        return OutVO.success(dtoList);
    }

}
