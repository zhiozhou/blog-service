package priv.zhou.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.domain.po.DictDataPO;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 字典数据 数据传输模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DictDataDTO extends DTO<DictDataPO>  {

    /**
     * 标识码
     */
    private String code;

    /**
     * 标签
     */
    private String label;

    /**
     * 字典标识
     */
    @NotBlank(message = "字典标识不可为空")
    private String dictKey;


    public DictDataDTO(DictDataPO dictDataPO) {
        super(dictDataPO);
    }

}