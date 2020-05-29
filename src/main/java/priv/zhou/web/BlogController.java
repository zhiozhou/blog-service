package priv.zhou.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.domain.Page;
import priv.zhou.domain.dto.BlogDTO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IBlogService;


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

    @RequestMapping("/get/{id}")
    public OutVO get(@PathVariable Integer id) {
        return blogService.get(id);
    }

    @RequestMapping("/type/get/{key}")
    public OutVO getType(@PathVariable String key) {
        return blogService.getType(key);
    }


    @RequestMapping("/list")
    public OutVO list(BlogDTO blogDTO, Page page) {
        return blogService.list(blogDTO, page);
    }
}
