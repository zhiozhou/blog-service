package priv.zhou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.annotation.CurrentVisitor;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.vo.OutVO;


/**
 * 访客 控制层
 *
 * @author zhou
 * @since 2020.05.15
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @RequestMapping("/get")
    public OutVO<VisitorDTO> get(@CurrentVisitor VisitorDTO visitorDTO) {
        // 生成特定token可能会使用
        return OutVO.success(new VisitorDTO()
                .setNickname(visitorDTO.getNickname())
                .setEmail(visitorDTO.getEmail())
                .setWebsite(visitorDTO.getWebsite()));
    }
}
