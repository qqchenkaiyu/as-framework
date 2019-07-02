package cn.witsky.bs.basicframework.database;//package cn.witsky.bs.basicframework.database;
//
//
//
//import com.google.common.cache.Cache;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 缓存服务。需要通过缓存回调服务提供缓存查找功能；
// * 如只需缓存不需要回调服务的，直接注入缓存使用即可。
// *
// * @author HuangYX
// */
//@Service
//public class CacheService {
//private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
//    @Autowired
//private RedisService redisService;
//    @Autowired
//private Cache<String, SecretNOInfo> telXInfoCache;
//
//    public SecretNOInfo getSecretNOInfo(final String telX) throws Exception {
//            return telXInfoCache.get(telX, () -> redisService.getSecretNOInfo(telX));
//    }
//
//
//}
