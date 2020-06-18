package priv.zhou.service.impl;


import org.springframework.stereotype.Service;
import priv.zhou.domain.Page;
import priv.zhou.domain.dao.CommentDAO;
import priv.zhou.domain.dto.CommentDTO;
import priv.zhou.domain.dto.DTO;
import priv.zhou.domain.po.CommentPO;
import priv.zhou.domain.vo.ListVO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.misc.OutVOEnum;
import priv.zhou.service.ICommentService;

import java.util.List;

/**
 * 菜单 服务层实现
 *
 * @author zhou
 * @since 2020.05.29
 */
@Service
public class CommentServiceImpl implements ICommentService {


    private final CommentDAO commentDAO;

    public CommentServiceImpl(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }


    @Override
    public OutVO<ListVO<CommentDTO>> list(CommentDTO commentDTO, Page page) {
        if (null == commentDTO.getBlogId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }
        Integer count = commentDAO.count(commentDTO);
        List<CommentPO> poList = 0 == count ? null : commentDAO.list(commentDTO, page);
        return OutVO.list(DTO.ofPO(poList, CommentDTO::new), page.getPage() < getTotalPage(count, page.getLimit()));
    }

    @Override
    public OutVO<ListVO<CommentDTO>> replyList(CommentDTO commentDTO, Page page) {
        if (null == commentDTO.getTopicId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }
        Integer count = commentDAO.count(commentDTO);
        List<CommentPO> poList = 0 == count ? null : commentDAO.listReply(commentDTO, page);
        return OutVO.list(DTO.ofPO(poList, CommentDTO::new), page.getPage() < getTotalPage(count, page.getLimit()));
    }


    private int getTotalPage(Integer count, Integer limit) {
        if (0 == count) {
            return 0;
        }
        int totalPage = count / limit;
        return 0 != count % limit ? totalPage + 1 : totalPage;
    }

}
