package cn.witsky.bs.basicframework.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);


    @Pointcut("@annotation(cn.witsky.bs.basicframework.anotation.RequestInLog)")
    public void log() {
    }
/*
打印入参 然后统一校验
 */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();


        Object[] args = joinPoint.getArgs();
        logger.info("url={},args={}", request.getRequestURL(), args);
        for (Object arg : args) {
            if(arg instanceof BeanPropertyBindingResult){
                BeanPropertyBindingResult bindingResult= (BeanPropertyBindingResult) arg;
                if (bindingResult.hasErrors()) {
                    throw  new Exception(bindingResult.getFieldError().getDefaultMessage());
                }
            }
        }

        //url

    }

//    @After("log()")
//    public void doAfter() {
//
//    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        logger.info("响应是={}", object.toString());
    }

}
