package priv.zhou.service.impl;

import org.springframework.stereotype.Service;
import priv.zhou.domain.dao.VisitorDAO;
import priv.zhou.domain.dto.DTO;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.po.VisitorPO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.misc.NULL;
import priv.zhou.misc.OutVOEnum;
import priv.zhou.service.IVisitorService;
import priv.zhou.tools.RedisUtil;
import priv.zhou.tools.TokenUtil;

import static priv.zhou.misc.CONSTANT.VISITOR_KEY;


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
    public OutVO<VisitorDTO> create() {
        VisitorPO visitorPO = new VisitorPO()
                .setState(0)
                .setNotify(0);
        return visitorDAO.save(visitorPO) > 0 ?
                OutVO.success(DTO.ofPO(visitorPO, VisitorDTO::new)) :
                OutVO.fail(OutVOEnum.FAIL_OPERATION);
    }

    @Override
    public OutVO<NULL> update(VisitorDTO visitorDTO) {
        if (null == visitorDTO.getId()) {
            return OutVO.fail(OutVOEnum.FAIL_PARAM);
        }

        VisitorPO visitorPO = visitorDTO.toPO();
        if (visitorDAO.update(visitorPO) < 1) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }
        RedisUtil.set(VISITOR_KEY + visitorDTO.getId(), visitorPO);
        return OutVO.success();
    }


    @Override
    public OutVO<VisitorDTO> get(String token) {
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
