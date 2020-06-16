package priv.zhou.service;

import priv.zhou.domain.Page;
import priv.zhou.domain.dto.CommentDTO;
import priv.zhou.domain.vo.ListVO;
import priv.zhou.domain.vo.OutVO;

import java.util.List;

/**
 * 评论 服务层定义
 *
 * @author zhou
 * @since 2020.06.16
 */
public interface ICommentService {

    OutVO<ListVO<CommentDTO>> list(CommentDTO commentDTO, Page page);

}
