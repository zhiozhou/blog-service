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
        boolean hasMore = false;
        List<CommentPO> poList = null;
        Integer count = commentDAO.count(commentDTO);
        if (0 != count) {
            Integer totalPage = count / page.getLimit();
            if (0 != count % page.getLimit()) {
                totalPage += 1;
            }
            hasMore = page.getPage() < totalPage;
            poList = commentDAO.list(commentDTO, page);
        }
        return OutVO.list(DTO.ofPO(poList, CommentDTO::new), hasMore);
    }

}
