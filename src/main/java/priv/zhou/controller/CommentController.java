package priv.zhou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.domain.Page;
import priv.zhou.domain.dto.CommentDTO;
import priv.zhou.domain.vo.ListVO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.ICommentService;


/**
 * 评论 控制层
 *
 * @author zhou
 * @since 2020.05.15
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping("/list")
    public OutVO<ListVO<CommentDTO>> list(CommentDTO commentDTO, Page page) {
        return commentService.list(commentDTO, page);
    }

    @RequestMapping("/reply/list")
    public OutVO<ListVO<CommentDTO>> replyList(CommentDTO commentDTO, Page page) {
        return commentService.replyList(commentDTO, page);
    }
}
