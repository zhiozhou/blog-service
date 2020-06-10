package priv.zhou.filter;


import lombok.extern.slf4j.Slf4j;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.params.OutVOEnum;
import priv.zhou.tools.HttpUtil;
import priv.zhou.tools.RedisUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static priv.zhou.params.CONSTANT.ACCESS_BLOCK_KEY;

@Slf4j
@WebFilter
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        System.out.println(HttpUtil.getUserAgent(request));
        String ip = HttpUtil.getIpAddress(request);
        if (RedisUtil.isMemberSet(ACCESS_BLOCK_KEY, ip)) {
            HttpUtil.out(response, OutVO.fail(OutVOEnum.ACCESS_BLOCK));
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {

    }

}