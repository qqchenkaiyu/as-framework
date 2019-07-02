package cn.witsky.bs.basicframework.handle;

import cn.witsky.bs.basicframework.utils.BasicUtil;
import cn.witsky.bs.basicframework.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handle(Exception e) {
        BasicUtil.handleErrorOutput(logger,e);
//        if (e instanceof PhoneNumberException) {
//            return ResultUtil.noResource();
//        }
       return ResultUtil.error(e.getMessage());
    }
}
