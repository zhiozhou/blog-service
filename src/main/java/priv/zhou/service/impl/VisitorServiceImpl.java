package priv.zhou.service.impl;

import org.springframework.stereotype.Service;
import priv.zhou.domain.dao.VisitorDAO;
import priv.zhou.domain.dto.DTO;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.po.VisitorPO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.params.NULL;
import priv.zhou.params.OutVOEnum;
import priv.zhou.service.IVisitorService;
import priv.zhou.tools.RedisUtil;
import priv.zhou.tools.TokenUtil;

import static priv.zhou.params.CONSTANT.VISITOR_KEY;


/**
 * 访客 服务层实现
 *
 * @author zhou
 * @since 2020.06.08
 */
@Service
public class VisitorServiceImpl implements IVisitorService {

    private final VisitorDAO visitorDAO;

    public VisitorServiceImpl(VisitorDAO visitorDAO) {
        this.visitorDAO = visitorDAO;
    }

    @Override
    public OutVO<NULL> update(VisitorDTO visitorDTO) {
        VisitorPO visitorPO = visitorDTO.toPO();
        return visitorDAO.update(visitorPO) > 0 ?
                OutVO.success() :
                OutVO.fail(OutVOEnum.FAIL_OPERATION);
    }


    @Override
    public OutVO<VisitorDTO> get(String token) throws Exception {
        Integer id = TokenUtil.parseId(token);
        if (null == id) {
            return OutVO.fail(OutVOEnum.NEED_INIT);
        }
        String visitorKey = VISITOR_KEY + id;
        VisitorPO visitorPO = (VisitorPO) RedisUtil.get(visitorKey);
        if (null == visitorPO) {
            visitorPO = visitorDAO.get(new VisitorDTO().setId(id));
            if (null == visitorPO) {
                return OutVO.fail(OutVOEnum.NEED_INIT);
            }
            RedisUtil.set(visitorKey, visitorPO);
        }
        return OutVO.success(DTO.ofPO(visitorPO, VisitorDTO::new));
    }

}
