package cn.witsky.bs.basicframework.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author: chenkaiyu
 * @create: 2019-06-10 16:07
 */
@Component
public class BeanValidator {
	@Autowired
	Validator validator;


	@PostConstruct
	public void init(){
//		validator.getConstraintsForClass(BindInfo.class);
//		validator.getConstraintsForClass(SecretNOInfo.class);
//		validator.getConstraintsForClass(AsaStartCallSubRequest.class);
	}
	/**
	 * 验证某个bean的参数
	 *
	 * @param object 被校验的参数
	 * @throws ValidationException 如果参数校验不成功则抛出此异常
	 */
	public  <T> void validate(T object) throws Exception {
		//执行验证
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
		//如果有验证信息，则将第一个取出来包装成异常返回
		ConstraintViolation<T> constraintViolation = getFirst(constraintViolations);
		if (constraintViolation != null) {
			throw new Exception(constraintViolation.getMessage());
		}
	}
	public  <T> void init(T object) throws Exception {
		//执行验证
		validator.getConstraintsForClass(object.getClass());
		//validator.unwrap(object.getClass());
	}
	private static <T> ConstraintViolation<T> getFirst(Set<ConstraintViolation<T>> constraintViolations) {
		if(constraintViolations.size()!=0){
			ConstraintViolation<T> next = constraintViolations.iterator().next();
			return next;
		}
		return null;
	}

}