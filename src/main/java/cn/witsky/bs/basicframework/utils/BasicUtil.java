package cn.witsky.bs.basicframework.utils;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: chenkaiyu
 * @create: 2019-06-10 20:01
 */
public class BasicUtil {
//统一logger error的格式
	public static void handleErrorOutput(Logger logger, Throwable ex) {
		StackTraceElement[] stackTrace = ex.getStackTrace();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(ex.getMessage()+"\n");
		for (StackTraceElement stackTraceElement : stackTrace) {
			stringBuffer.append("at "+stackTraceElement.toString()+"\n");
		}
		logger.error(stringBuffer.toString());
	}

	/**
	 * 只复制非空属性
	 * @param source
	 * @param target
	 */
	public static void copyProperties(Object source, Object target) {
		BeanUtils.copyProperties(source,target,getNullPropertyNames(source));
	}
	public static String[] getNullPropertyNames(Object source) {

		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
