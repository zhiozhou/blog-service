package priv.zhou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.annotation.AccessLog;
import priv.zhou.domain.Page;
import priv.zhou.domain.dto.BlogDTO;
import priv.zhou.domain.dto.BlogTypeDTO;
import priv.zhou.domain.vo.ListVO;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.service.IBlogService;
import priv.zhou.tools.DateUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static priv.zhou.tools.DateUtil.YMDHMS;


/**
 * 博客 控制层
 *
 * @author zhou
 * @since 2020.05.15
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    private final IBlogService blogService;

    public BlogController(IBlogService blogService) {
        this.blogService = blogService;
    }


    @AccessLog
    @RequestMapping("/get")
    public OutVO<BlogDTO> get(BlogDTO blogDTO) {
        return blogService.get(blogDTO);
    }


    @RequestMapping("/type/get")
    public OutVO<BlogTypeDTO> getType(@Valid BlogTypeDTO blogTypeDTO) {
        return blogService.getType(blogTypeDTO);
    }


    @RequestMapping("/list")
    public OutVO<ListVO<BlogDTO>> list(BlogDTO blogDTO, Page page) {
        return blogService.list(blogDTO, page);
    }

    public static void main(String[] args) {
        List<Region> list = new ArrayList<>();
        list.add(new Region(
                DateUtil.parse("2020-11-11 10:00:00", YMDHMS),
                DateUtil.parse("2020-11-11 10:15:00", YMDHMS)));
        list.add(new Region(
                DateUtil.parse("2020-11-11 10:10:00", YMDHMS),
                DateUtil.parse("2020-11-11 10:20:00", YMDHMS)));
        list.add(new Region(
                DateUtil.parse("2020-11-11 09:50:00", YMDHMS),
                DateUtil.parse("2020-11-11 10:05:00", YMDHMS)
        ));
        list.add( new Region(
                DateUtil.parse("2020-11-11 11:05:00", YMDHMS),
                DateUtil.parse("2020-11-11 11:15:00", YMDHMS)
        ));

        for (int i = 0; i < list.size(); i++) {
            Region r1 = list.get(i);
            Date startTime = r1.getStartTime(), endTime = r1.getEndTime();
            for (int j = i + 1; j < list.size(); j++) {
                Region r2 = list.get(j);
                if (DateUtil.inRegion(r2.getStartTime(), startTime, endTime) ||
                        DateUtil.inRegion(r2.getEndTime(), startTime, endTime)) {
                    startTime = startTime.before(r2.getStartTime()) ? startTime : r2.getStartTime();
                    endTime = endTime.after(r2.getEndTime()) ? endTime : r2.getEndTime();
                    list.remove(r2);
                    j--;
                }
            }
            System.out.println();
        }
    }


    static class Region {

        Date startTime;

        Date endTime;

        public Region(Date startTime, Date endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public Date getStartTime() {
            return startTime;
        }

        public Date getEndTime() {
            return endTime;
        }
    }
}
