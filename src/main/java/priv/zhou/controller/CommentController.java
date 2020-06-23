package priv.zhou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.annotation.CurrentVisitor;
import priv.zhou.domain.Page;
import priv.zhou.domain.dto.CommentDTO;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.vo.ListVO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.misc.NULL;
import priv.zhou.service.ICommentService;

import javax.validation.Valid;


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

    @RequestMapping("/save")
    public OutVO<NULL> save(@CurrentVisitor VisitorDTO visitorDTO, @Valid VisitorDTO inputDTO, @Valid CommentDTO commentDTO) {
        return commentService.save(visitorDTO, inputDTO, commentDTO);
    }

    @RequestMapping("/list")
    public OutVO<ListVO<CommentDTO>> list(@CurrentVisitor VisitorDTO visitorDTO, CommentDTO commentDTO, Page page) {
        return commentService.list(visitorDTO, commentDTO, page);
    }

    @RequestMapping("/reply/list")
    public OutVO<ListVO<CommentDTO>> replyList(@CurrentVisitor VisitorDTO visitorDTO, CommentDTO commentDTO, Page page) {
        return commentService.replyList(visitorDTO, commentDTO, page);
    }
}
