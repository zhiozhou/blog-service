package priv.zhou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import priv.zhou.domain.Page;
import priv.zhou.domain.dao.BlogDAO;
import priv.zhou.domain.dao.BlogTypeDAO;
import priv.zhou.domain.dto.BlogDTO;
import priv.zhou.domain.dto.BlogTypeDTO;
import priv.zhou.domain.dto.DTO;
import priv.zhou.domain.po.BlogPO;
import priv.zhou.domain.po.BlogTypePO;
import priv.zhou.domain.vo.ListVO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.misc.OutVOEnum;
import priv.zhou.service.IBlogService;
import priv.zhou.tools.RedisUtil;

import java.util.List;

import static priv.zhou.misc.CONSTANT.*;


/**
 * 博客 服务层实现
 *
 * @author zhou
 * @since 2020.05.15
 */
@Service
public class BlogServiceImpl implements IBlogService {

    private final BlogDAO blogDAO;

    private final BlogTypeDAO blogTypeDAO;

    public BlogServiceImpl(BlogDAO blogDAO, BlogTypeDAO blogTypeDAO) {
        this.blogDAO = blogDAO;
        this.blogTypeDAO = blogTypeDAO;
    }

    @Override
    public OutVO<BlogDTO> get(BlogDTO blogDTO) {

        // 1.校验参数
        if (null == blogDTO.getId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        // 2.获取博客
        String poKey = BLOG_KEY + blogDTO.getId();
        BlogPO blogPO = (BlogPO) RedisUtil.get(poKey);
        if (null == blogPO) {
            if (null == (blogPO = blogDAO.get(blogDTO))) {
                return OutVO.fail(OutVOEnum.NOT_FOUND);
            } else if (!Integer.valueOf(7).equals(blogPO.getType().getState())) {
                BlogDTO queryDTO = new BlogDTO()
                        .setId(blogPO.getId())
                        .setType(new BlogTypeDTO().setKey(blogPO.getType().getKey()));
                blogPO.setPrev(blogDAO.getPrev(queryDTO))
                        .setNext(blogDAO.getNext(queryDTO));
            }
            RedisUtil.set(poKey, blogPO);
        }

        // 3.增加访问数量
        // TODO: 部署多体改为 Redis 同步
        synchronized (BLOG_PV_KEY) {
            String pvKey = blogPO.getId().toString();
            Long pv = (Long) RedisUtil.getHash(BLOG_PV_KEY, pvKey);
            if (null == pv) {
                pv = blogPO.getPv();
            }
            RedisUtil.putHash(BLOG_PV_KEY, pvKey, blogPO.setPv(++pv).getPv());
        }
        return OutVO.success(new BlogDTO(blogPO));
    }


    @Override
    public OutVO<BlogTypeDTO> getType(BlogTypeDTO blogTypeDTO) {
        String typeKye = BLOG_TYPE_KEY + blogTypeDTO.getKey();
        BlogTypePO blogTypePO = (BlogTypePO) RedisUtil.get(typeKye);
        if (null == blogTypePO) {
            if (null == (blogTypePO = blogTypeDAO.get(blogTypeDTO))) {
                return OutVO.fail(OutVOEnum.NOT_FOUND);
            }
            RedisUtil.set(typeKye, blogTypePO);
        }
        return OutVO.success(new BlogTypeDTO(blogTypePO));
    }


    @Override
    public OutVO<ListVO<BlogDTO>> list(BlogDTO blogDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<BlogPO> poList = blogDAO.list(blogDTO);
        PageInfo<BlogPO> pageInfo = new PageInfo<>(poList);
        long totalPage = pageInfo.getTotal() / page.getLimit();
        if (totalPage % page.getLimit() != 0) {
            totalPage += 1;
        }
        return OutVO.list(DTO.ofPO(poList, BlogDTO::new), page.getPage() < totalPage);
    }
}
