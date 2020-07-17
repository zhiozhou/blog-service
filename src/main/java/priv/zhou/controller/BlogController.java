package priv.zhou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.annotation.AccessLog;
import priv.zhou.domain.Page;
import priv.zhou.domain.dto.BlogDTO;
import priv.zhou.domain.dto.BlogTypeDTO;
import priv.zhou.domain.vo.ListVO;
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


    @AccessLog
    @RequestMapping("/get")
    public OutVO<BlogDTO> get(BlogDTO blogDTO) {
        return blogService.get(blogDTO);
    }


    @RequestMapping("/type/get")
    public OutVO<BlogTypeDTO> getType(@Valid BlogTypeDTO blogTypeDTO) {
        return blogService.getType(blogTypeDTO);
    }


    @RequestMapping("/list")
    public OutVO<ListVO<BlogDTO>> list(BlogDTO blogDTO, Page page) {
        return blogService.list(blogDTO, page);
    }
}
