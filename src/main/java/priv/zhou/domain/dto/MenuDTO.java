package priv.zhou.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.domain.po.MenuPO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * 菜单 数据传输模型
 *
 * @author zhou
 * @since 2020.05.29
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO extends DTO<MenuPO> implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 父级id
     */
    private Integer parentId;


    /**
     * 名称
     */
    private String name;


    /**
     * 图标
     */
    private String icon;


    /**
     * 路径
     */
    private String path;

    /**
     * 类型 0 目录 1 菜单
     */
    private Integer type;

    /**
     * 权限标识
     */
    private String key;

    /**
     * 博客类型状态
     */
    private Integer blogTypeState;


    /**
     * 子级列表
     */
    private List<MenuDTO> childList;



    public MenuDTO(MenuPO menuPO) {
        super(menuPO);
    }
}