package priv.zhou.service;

import priv.zhou.domain.Page;
import priv.zhou.domain.dto.CommentDTO;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.vo.ListVO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.misc.NULL;

/**
 * 评论 服务层定义
 *
 * @author zhou
 * @since 2020.06.16
 */
public interface ICommentService {

    OutVO<NULL> save(VisitorDTO visitorDTO, CommentDTO commentDTO);

    OutVO<ListVO<CommentDTO>> list(VisitorDTO visitorDTO, CommentDTO commentDTO, Page page);

    OutVO<ListVO<CommentDTO>> replyList(VisitorDTO visitorDTO, CommentDTO commentDTO, Page page);

}
