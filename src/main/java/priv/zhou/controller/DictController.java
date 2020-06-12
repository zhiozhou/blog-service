package priv.zhou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.annotation.SkipVersion;
import priv.zhou.domain.dto.DictDataDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IDictService;
import priv.zhou.tools.CookieUtil;
import priv.zhou.tools.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static priv.zhou.misc.CONSTANT.TOKEN_KEY;
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

    public final static String SNS_KEY = "zhou_sns";

    public DictController(IDictService dictService) {
        this.dictService = dictService;
    }

    @SkipVersion
    @RequestMapping("/snsMap")
    public OutVO<Map<String, DictDataDTO>> snsMap() {
        return dictService.dataMap(new DictDataDTO().setDictKey(SNS_KEY));
    }


}
