package cn.witsky.bs.basicframework.database;//package cn.witsky.bs.basicframework.database;
//
//import cn.witsky.basicframework.Constants.RedisKeys;
//import cn.witsky.basicframework.domain.apk.*;
//import cn.witsky.basicframework.utils.BeanValidator;
//import cn.witsky.bs.config.Config;
//import cn.witsky.ccas.domain.SecretNOInfo;
//import com.alibaba.fastjson.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.Map;
//
///**
// *
// * @author chenkaiyu
// */
//@Service
//public class RedisService {
//private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
//
//private final StringRedisTemplate stringRedisTemplate;
//        HashOperations<String, String, String> hash;
//
//        public RedisService(StringRedisTemplate stringRedisTemplate,
//							Config config) {
//        this.stringRedisTemplate = stringRedisTemplate;
//       hash = stringRedisTemplate.opsForHash();
//}
//
//public ApkConfig getApkConfigFromRedis  (String apk) throws IOException {
//        if (apk == null) {
//        throw new NullPointerException("appkey cannot be null");
//        }
//        apk=apk.split("_")[0];
//        Map<String, String> entries = hash.entries(RedisKeys.Apk.APPKEY_INFO + apk);
//        if (entries == null || entries.size() == 0) {
//                logger.error("appkey信息异常  appkey= "+apk);
//                return null;
//        }
//                return getApkConfigFromMap(entries);
//        }
//
//        private ApkConfig getApkConfigFromMap(Map<String, String> entries) {
//
//                final String wholeAppkeys = entries.get(RedisKeys.Apk.WHOLE_APPKEY);
//                final String secretkey = entries.get(RedisKeys.Apk.SECRET_KEY);
//                final ApkServiceRouteTable serviceRouteTable = JSONObject.parseObject(
//                        entries.get(RedisKeys.Apk.SERVICE_ROUTE_TABLE), ApkServiceRouteTable.class);
//                final ApkExtCriteriaTable extCriteriaTable = JSONObject.parseObject(
//                        entries.get(RedisKeys.Apk.EXT_CRITERIA_TABLE), ApkExtCriteriaTable.class);
//                final ApkServiceFunction serviceFunction = JSONObject.parseObject(
//                        entries.get(RedisKeys.Apk.SERVICE_FUNCTION), ApkServiceFunction.class);
//                final ApkServicePayment servicePayment = JSONObject.parseObject(
//                        entries.get(RedisKeys.Apk.SERVICE_PAYMENT), ApkServicePayment.class);
//                final ApkServicePushCriteria servicePushCriteria = JSONObject.parseObject(
//                        entries.get(RedisKeys.Apk.SERVICE_PUSH_CRITERIA), ApkServicePushCriteria.class);
//                final ApkServiceAgentCriteria serviceAgentCriteria = JSONObject.parseObject(
//                        entries.get(RedisKeys.Apk.SERVICE_AGENT_CRITERIA), ApkServiceAgentCriteria.class);
//                ApkConfig build = ApkConfig.builder()
//                        .wholeAppkey(wholeAppkeys)
//                        .secretkey(secretkey)
//                        .serviceRouteTable(serviceRouteTable)
//                        .extCriteriaTable(extCriteriaTable)
//                        .serviceFunction(serviceFunction)
//                        .servicePayment(servicePayment)
//                        .servicePushCriteria(servicePushCriteria)
//                        .serviceAgentCriteria(serviceAgentCriteria)
//                        .build();
//                return build;
//        }
//
//
//        public SecretNOInfo getSecretNOInfo(String telX) throws Exception {
//                    HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
//                    String xInfoKey = RedisKeys.createXInfoKey(telX);
//                    Map<String, String> entries = hashOperations.entries(xInfoKey);
//                    SecretNOInfo secretNOInfo = new SecretNOInfo();
//                    secretNOInfo.setAppkey(entries.get(RedisKeys.X.APPKEY));
//                    secretNOInfo.setServiceType(entries.get(RedisKeys.X.SERVICETYPE));
//                    secretNOInfo.setDnTag(entries.get(RedisKeys.X.DNTAG));
//                    secretNOInfo.setSmcgt(entries.get(RedisKeys.X.SMCGT));
//                    secretNOInfo.setAreacode(entries.get(RedisKeys.X.AREACODE));
//                    secretNOInfo.setDn(telX);
//                    BeanValidator.validate(secretNOInfo);
//                    return null;
//	}
//}
