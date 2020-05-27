package priv.zhou.service.impl;

import com.alibaba.fastjson.JSONObject;
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
import priv.zhou.domain.vo.OutVO;
import priv.zhou.params.OutVOEnum;
import priv.zhou.service.IBlogService;
import priv.zhou.tools.RedisUtil;

import java.util.List;

import static priv.zhou.params.CONSTANT.*;


/**
 * 服务层实现
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
    public OutVO get(Integer id) {
        String poKey = BLOG_KEY + id;
        BlogPO blogPO = (BlogPO) RedisUtil.get(poKey);
        if (null == blogPO) {
            BlogDTO queryDTO = new BlogDTO().setId(id);
            if (null == (blogPO = blogDAO.get(queryDTO))) {
                return OutVO.fail(OutVOEnum.NOT_FOUND);
            }
            queryDTO.setType(blogPO.getType());
            blogPO.setPrev(blogDAO.getPrev(queryDTO))
                    .setNext(blogDAO.getNext(queryDTO));
            RedisUtil.set(poKey, blogPO);
        }

        // TODO: 部署多体改为Reids同步
        synchronized (BLOG_PV_KEY) {
            String pvKey = id.toString();
            Long pv = (Long) RedisUtil.getHash(BLOG_PV_KEY, pvKey);
            if (null == pv) {
                pv = blogPO.getPv();
            }
            RedisUtil.putHash(BLOG_PV_KEY, pvKey, ++pv);
            blogPO.setPv(pv);
        }
        return OutVO.success(new BlogDTO(blogPO));
    }


    @Override
    public OutVO getType(String key) {
        String typeKye = BLOG_TYPE_KEY + key;
        BlogTypePO blogTypePO = (BlogTypePO) RedisUtil.get(typeKye);
        if (null == blogTypePO) {
            if (null == (blogTypePO = blogTypeDAO.get(new BlogTypeDTO().setKey(key)))) {
                return OutVO.fail(OutVOEnum.NOT_FOUND);
            }
            RedisUtil.set(typeKye, blogTypePO);
        }
        return OutVO.success(new BlogTypeDTO(blogTypePO));
    }


    @Override
    public OutVO list(BlogDTO blogDTO, Page page) {

        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<BlogPO> poList = blogDAO.list(blogDTO);
        PageInfo<BlogPO> pageInfo = new PageInfo<>(poList);
        long totalPage = pageInfo.getTotal() / page.getLimit();
        if (totalPage % page.getLimit() != 0) {
            totalPage += 1;
        }
        JSONObject data = new JSONObject();
        data.put("list", DTO.ofPO(poList, BlogDTO::new));
        data.put("hasMore", page.getPage() < totalPage);
        return OutVO.success(data);
    }
}
