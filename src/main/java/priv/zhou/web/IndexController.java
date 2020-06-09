package priv.zhou.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.params.OutVOEnum;
import priv.zhou.service.IVisitorService;

@RestController
public class IndexController {

    private final IVisitorService visitorService;

    public IndexController(IVisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping("/init")
    public OutVO<?> init(String token, String version) throws Exception {
        OutVO<VisitorDTO> getRes = visitorService.get(token);
        if (OutVOEnum.NEED_INIT.getCode().equals(getRes.getCode())) {


        } else {
            return getRes;
        }


        return null;
    }
}