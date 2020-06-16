package priv.zhou;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import priv.zhou.domain.Page;
import priv.zhou.domain.dao.CommentDAO;
import priv.zhou.domain.dto.CommentDTO;
import priv.zhou.domain.po.CommentPO;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TEST {

    @Autowired
    private CommentDAO commentDAO;

    @Test
    public void test() {

        List<CommentPO> list = commentDAO.list(new CommentDTO().setBlogId(1),new Page().setPage(2).setLimit(2));

        System.out.println("");
    }

}
