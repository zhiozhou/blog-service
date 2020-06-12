package priv.zhou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.domain.dto.DictDataDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IDictService;
import priv.zhou.tools.CookieUtil;
import priv.zhou.tools.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static priv.zhou.params.CONSTANT.TOKEN_KEY;
import static priv.zhou.tools.TokenUtil.SNS_VERSION;


/**
 * 字典 控制层
 *
 * @author zhou
 * @since 2020.05.15
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    private final IDictService dictService;

    public DictController(IDictService dictService) {
        this.dictService = dictService;
    }

    @RequestMapping("/snsMap")
    public OutVO<Map<String, DictDataDTO>> snsMap(HttpServletRequest request, HttpServletResponse response) {
        String snsKey = "zhou_sns";
        Map<String, Object> tokenMap = TokenUtil.parse(CookieUtil.get(TOKEN_KEY, request));
        OutVO<Map<String, DictDataDTO>> outVO = dictService.dataMap(new DictDataDTO().setDictKey(snsKey));
        if (outVO.isFail() || null == tokenMap) {
            return outVO;
        }
        tokenMap.put(SNS_VERSION, dictService.latestVersion(snsKey));
        response.addCookie(CookieUtil.create(TOKEN_KEY, TokenUtil.build(tokenMap)));
        return outVO;

    }


}
