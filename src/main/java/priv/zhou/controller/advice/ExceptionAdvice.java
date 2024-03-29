package priv.zhou.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import priv.zhou.domain.vo.OutVO;
import priv.zhou.exception.GlobalException;
import priv.zhou.misc.AppProperties;
import priv.zhou.misc.OutVOEnum;
import priv.zhou.tools.EmailUtil;
import priv.zhou.tools.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * controller 异常控制
 *
 * @author zhou
 * @since 2020.03.04
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    private final AppProperties appProperties;

    public ExceptionAdvice(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * 全局异常
     */
    @SuppressWarnings("all")
    @ExceptionHandler(value = Exception.class)
    public void globalHand(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        if (e instanceof GlobalException) {
            globalFailHand(request, response, (GlobalException) e);
            return;
        }
        StringBuilder builder = new StringBuilder("未知异常: request -->")
                .append(request.getRequestURI()).append(" | ")
                .append("请求参数 -->").append(HttpUtil.getParams(request)).append(" | ")
                .append("e -->");
        log.error(builder.toString(), e);
        HttpUtil.out(response, OutVO.fail(OutVOEnum.ERROR_SYSTEM));
        if (appProperties.isEmail()) {
            EmailUtil.send(appProperties.getAdminEmail(), appProperties.getName() + " 出现未知异常", getStackTrace(e));
        }
    }


    /**
     * 请求方法异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void NotSupportedHand() {
        log.info("Request method not supported");
    }


    /**
     * 验证异常
     */
    @ExceptionHandler(BindException.class)
    public void bindHand(HttpServletRequest request, HttpServletResponse response, BindException e) {
        List<ObjectError> errs = e.getBindingResult().getAllErrors();
        OutVO<?> outVO = OutVO.fail(OutVOEnum.FAIL_PARAM).setInfo(errs.get(0).getDefaultMessage());
        log.info("退出 {} 接口,返回报文 -->{}\n", request.getRequestURI(), outVO);
        HttpUtil.out(response, outVO);
    }

    /**
     * 全局错误异常
     */
    @ExceptionHandler(GlobalException.class)
    public void globalFailHand(HttpServletRequest request, HttpServletResponse response, GlobalException e) {
        log.info("退出 {} 接口,返回报文 -->{}\n", request.getRequestURI(), e.getOutVO());
        HttpUtil.out(response, e.getOutVO());
    }


    private String getStackTrace(Throwable e) {
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(outStream)) {
            e.printStackTrace(writer);
            writer.flush();
            return outStream.toString(AppProperties.ENC);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
