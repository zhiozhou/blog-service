package priv.zhou.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.domain.dto.DictDataDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IDictService;

import javax.validation.Valid;


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

    @RequestMapping("/dataList")
    public OutVO list(@Valid  DictDataDTO dictDataDTO) {
        return dictService.listData(dictDataDTO);
    }
}
