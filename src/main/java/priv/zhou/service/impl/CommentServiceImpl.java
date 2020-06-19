package priv.zhou.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import priv.zhou.domain.Page;
import priv.zhou.domain.dao.CommentDAO;
import priv.zhou.domain.dao.VisitorDAO;
import priv.zhou.domain.dto.CommentDTO;
import priv.zhou.domain.dto.DTO;
import priv.zhou.domain.dto.VisitorDTO;
import priv.zhou.domain.po.CommentPO;
import priv.zhou.domain.vo.ListVO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.misc.NULL;
import priv.zhou.misc.OutVOEnum;
import priv.zhou.service.ICommentService;
import priv.zhou.tools.CheckUtil;
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


    private final VisitorDAO visitorDAO;

    private final CommentDAO commentDAO;

    public CommentServiceImpl(VisitorDAO visitorDAO, CommentDAO commentDAO) {
        this.visitorDAO = visitorDAO;
        this.commentDAO = commentDAO;
    }

    @Override
    public OutVO<NULL> save(VisitorDTO visitorDTO, CommentDTO commentDTO) {
        VisitorDTO fromVisitor = commentDTO.getFromVisitor();
        if (StringUtils.isBlank(fromVisitor.getNickname())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM, "昵称不可为空！");
        } else if (StringUtils.isNotBlank(fromVisitor.getEmail())) {
            if (CheckUtil.notEmail(fromVisitor.getEmail())) {
                return OutVO.fail(OutVOEnum.EMPTY_PARAM, "邮箱格式对嘛？");
            } else if (!fromVisitor.getEmail().equals(visitorDTO.getEmail())) {
                fromVisitor.setAvatar(getGravatar(fromVisitor.getEmail()));
            }
        }
        fromVisitor.setId(visitorDTO.getId())
                .setLastAccessTime(new Date());
        return visitorDAO.update(fromVisitor.toPO()) > 0 && commentDAO.save(commentDTO.toPO()) > 0 ?
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


    public static void main(String[] args) {
        System.out.printf("https://s.gravatar.com/avatar/%s?s=48&r=g&d=identicon", Md5Util.encrypt("2080211280@qq.com"));
    }

}
