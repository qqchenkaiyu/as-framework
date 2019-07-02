package cn.witsky.bs.basicframework.aspect;

import cn.witsky.bs.basicframework.utils.ResultUtil;
import cn.witsky.bs.config.Config;
import cn.witsky.bs.utils.SignUtils;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;


@Aspect
@Component
public class SignAspect {

    private final static Logger logger = LoggerFactory.getLogger(SignAspect.class);

@Autowired
Config config;
    @Value("${witsky.sign-headers}")
    String signheader;
    @Pointcut("@annotation(cn.witsky.bs.basicframework.anotation.RequestSign)")
    public void sign() {
    }

    /*
	打印入参 然后统一校验
	 */
    @Around("sign()")
    public Object sign (ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object[] args = joinPoint.getArgs();
        //获取待加密的头域
        Map headerMap = ResultUtil.getSignHeaderMap(request,signheader);
        String msgdgt = headerMap.remove("msgdgt").toString();
        //如果有requestbody  就也加入加密的map
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            Annotation[] pas = annotations[i];
            for (Annotation annotation : pas) {
                if (annotation instanceof RequestBody) {
                    String jsonString=null;
                  //  String jsonString = JSONObject.toJSONString(args[0]);
                    if(!(args[0] instanceof  String)){
                         jsonString = JSONObject.toJSONString(args[0]);
}else{jsonString= (String) args[0];}
                    headerMap.putAll(JSONObject.parseObject(jsonString));

                }
            }
        }
        //验签不通过就不执行
        String sign = SignUtils.signature(headerMap, config.getSecret());
        if(!sign.equals(msgdgt)){
            logger.info("验签失败 msgdgt={},sign={}",msgdgt,sign);
            return ResultUtil.signFail();
        }
        logger.info("验签成功 msgdgt={},sign={}",msgdgt,sign);
        Object proceed = joinPoint.proceed();
        return proceed;
    }

}
