package cn.witsky.bs.basicframework.aspect;//package cn.witsky.basicframework.aspect;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.stereotype.Component;
//
///**
// * @author CKY
// */
//@Aspect
//@Component
//@EnableScheduling
//public class TimeCostAspect {
//    private static final Logger logger = LoggerFactory.getLogger(TimeCostAspect.class);
//
//
//    @Pointcut("@annotation(cn.witsky.smb.annotation.TimeCost)")
//    public void TimeCost() {
//    }
//
//    @Around("TimeCost()")
//    public Object beforeRest(ProceedingJoinPoint joinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        Object proceed = joinPoint.proceed();
//        long end = System.currentTimeMillis();
//        logger.debug(joinPoint.getSignature()+"花费了"+(end-start)+"毫秒");
//        return proceed;
//    }
//
//}
