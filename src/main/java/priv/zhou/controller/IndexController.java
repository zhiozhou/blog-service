package priv.zhou.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.domain.dto.DictDataDTO;
import priv.zhou.domain.dto.MenuDTO;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.params.OutVOEnum;
import priv.zhou.service.IDictService;
import priv.zhou.service.IMenuService;
import priv.zhou.service.IVisitorService;
import priv.zhou.tools.ParseUtil;
import priv.zhou.tools.TokenUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static priv.zhou.params.CONSTANT.SNS_DICT_KEY;
import static priv.zhou.tools.TokenUtil.*;

@RestController
public class IndexController {

    private final IMenuService menuService;

    private final IDictService dictService;

    private final IVisitorService visitorService;

    public IndexController(IMenuService menuService, IDictService dictService, IVisitorService visitorService) {
        this.menuService = menuService;
        this.dictService = dictService;
        this.visitorService = visitorService;
    }

    @PostMapping("/init")
    public OutVO<?> init(String token) throws Exception {

        OutVO<VisitorDTO> visitorRes = visitorService.get(token);
        if (visitorRes.isFail()) {
            if (!OutVOEnum.NEED_INIT.getCode().equals(visitorRes.getCode()) ||
                    (visitorRes = visitorService.create()).isFail()) {
                return visitorRes;
            }
        }

        VisitorDTO visitorDTO = visitorRes.getData();
        Map<String, Object> tokenMap = TokenUtil.parse(token);
        Long menuLatest = menuService.latestVersion(), snsLatest = dictService.latestVersion(SNS_DICT_KEY);

        JSONObject data = new JSONObject();
        if (!TokenUtil.verify(tokenMap)) {
            tokenMap = new HashMap<>();
            tokenMap.put(VISITOR_ID, visitorDTO.getId());
        }
        if (menuLatest > ParseUtil.longer(tokenMap.get(MENU_VERSION))) {
            tokenMap.put(MENU_VERSION, menuLatest);
            OutVO<List<MenuDTO>> menuListRes = menuService.list();
            if (menuListRes.isFail()) {
                return menuListRes;
            }
            data.put("menuList", menuListRes.getData());
        }
        if (snsLatest > ParseUtil.longer(tokenMap.get(SNS_VERSION))) {
            tokenMap.put(SNS_VERSION, snsLatest);
            OutVO<Map<String, DictDataDTO>> snsRes = dictService.dataMap(new DictDataDTO().setDictKey(SNS_DICT_KEY));
            if (snsRes.isFail()) {
                return snsRes;
            }
            data.put("snsMap", snsRes.getData());
        }
        data.put("token", TokenUtil.build(tokenMap));
        return OutVO.success(data);
    }
}