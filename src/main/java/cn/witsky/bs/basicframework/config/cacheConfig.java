package cn.witsky.bs.basicframework.config;//package cn.witsky.basicframework.config;
//
//import cn.witsky.basicframework.domain.SecretNOInfo;
//import com.google.common.cache.Cache;
//import com.google.common.cache.CacheBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * @author: chenkaiyu
// * @create: 2019-06-10 20:05
// */
//@Configuration
//public class cacheConfig {
//	/**
//	 * 缓存中间虚拟号码信息
//	 *
//	 * @return 中间虚拟号码cache
//	 */
//	@Bean(name = "cacheForTelXInfo")
//	public Cache<String, SecretNOInfo> guavaCacheForTelXInfo() {
//		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
//		//缓存在10分钟未更新的情况下会删除
//		cacheBuilder.expireAfterWrite(10, TimeUnit.MINUTES);
//		return cacheBuilder.build();
//	}
//}
