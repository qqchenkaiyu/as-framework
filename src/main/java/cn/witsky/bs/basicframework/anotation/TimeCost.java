package cn.witsky.bs.basicframework.anotation;

import java.lang.annotation.*;

/**
 * 记录时间消耗，

 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TimeCost {

}
