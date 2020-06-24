package priv.zhou.service.impl;


import org.springframework.stereotype.Service;
import priv.zhou.domain.Page;
import priv.zhou.domain.dao.CommentDAO;
import priv.zhou.domain.dto.CommentDTO;
import priv.zhou.domain.dto.DTO;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.po.CommentPO;
import priv.zhou.domain.vo.ListVO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.misc.NULL;
import priv.zhou.misc.OutVOEnum;
import priv.zhou.service.ICommentService;
import priv.zhou.service.IVisitorService;
import priv.zhou.tools.Md5Util;

import java.util.Date;
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

    private final IVisitorService visitorService;

    public CommentServiceImpl(CommentDAO commentDAO, IVisitorService visitorService) {
        this.commentDAO = commentDAO;
        this.visitorService = visitorService;
    }

    @Override
    public OutVO<NULL> save(VisitorDTO visitorDTO, VisitorDTO inputDTO, CommentDTO commentDTO) {

        // 1.校验参数
        if (null != commentDTO.getRepliedId()) {
            CommentPO targetPO = commentDAO.get(new CommentDTO().setId(commentDTO.getRepliedId()));
            if (null == targetPO || targetPO.getState().equals(0)) {
                return OutVO.fail(OutVOEnum.FAIL_PARAM);
            }
            commentDTO.setTopicId(targetPO.getTopicId())
                    .setToVisitor(new VisitorDTO().setId(targetPO.getFromVisitor().getId()));
        }

        // 2.补充参数
        visitorDTO.setNickname(inputDTO.getNickname())
                .setWebsite(inputDTO.getWebsite())
                .setLastAccessTime(new Date());
        if (null != inputDTO.getEmail() && !inputDTO.getEmail().equals(visitorDTO.getEmail())) {
            visitorDTO.setEmail(inputDTO.getEmail())
                    .setAvatar(getGravatar(inputDTO.getEmail()));
        }

        // 3.修改访客
        OutVO<NULL> updateRes = visitorService.update(visitorDTO);
        if (updateRes.isFail()) {
            return updateRes;
        }

        // 4.保存评论
        return commentDAO.save(commentDTO.setFromVisitor(visitorDTO).toPO()) > 0 ?
                OutVO.success() :
                OutVO.fail(OutVOEnum.LATER_RETRY);
    }

    @Override
    public OutVO<ListVO<CommentDTO>> list(VisitorDTO visitorDTO, CommentDTO commentDTO, Page page) {
        if (null == commentDTO.getBlogId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }
        Integer count = commentDAO.count(commentDTO.setFromVisitor(visitorDTO));
        List<CommentPO> poList = 0 == count ? null : commentDAO.list(commentDTO, page);
        return OutVO.list(DTO.ofPO(poList, CommentDTO::new), page.getPage() < getTotalPage(count, page.getLimit()));
    }

    @Override
    public OutVO<ListVO<CommentDTO>> replyList(VisitorDTO visitorDTO, CommentDTO commentDTO, Page page) {
        if (null == commentDTO.getTopicId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }
        Integer count = commentDAO.count(commentDTO.setFromVisitor(visitorDTO));
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

    private String getGravatar(String email) {
        return String.format("https://s.gravatar.com/avatar/%s?s=48&r=g&d=identicon", Md5Util.encrypt(email));
    }
}
