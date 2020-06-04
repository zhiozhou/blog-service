package priv.zhou.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.domain.Page;
import priv.zhou.domain.dto.BlogDTO;
import priv.zhou.domain.dto.BlogTypeDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IBlogService;

import javax.validation.Valid;


/**
 * 博客 控制层
 *
 * @author zhou
 * @since 2020.05.15
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    private final IBlogService blogService;

    public BlogController(IBlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping("/get")
    public OutVO get(BlogDTO blogDTO) {
        return blogService.get(blogDTO);
    }

    @RequestMapping("/type/get")
    public OutVO getType(@Valid  BlogTypeDTO blogTypeDTO) {
        return blogService.getType(blogTypeDTO);
    }


    @RequestMapping("/list")
    public OutVO list(BlogDTO blogDTO, Page page) {
        return blogService.list(blogDTO, page);
    }
}
