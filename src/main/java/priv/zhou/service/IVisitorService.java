package priv.zhou.service;


import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.params.NULL;

/**
 * 访客 服务层定义
 *
 * @author zhou
 * @since 2020.06.08
 */
public interface IVisitorService {

    OutVO<VisitorDTO> create();

    OutVO<NULL> update(VisitorDTO visitorDTO);

    OutVO<VisitorDTO> get(String token) throws Exception;
}
