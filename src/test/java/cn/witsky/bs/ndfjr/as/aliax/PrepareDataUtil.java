package cn.witsky.bs.ndfjr.as.aliax;


import cn.witsky.bs.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: chenkaiyu
 * @create: 2019-05-29 13:51
 */
@Component
public class PrepareDataUtil {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
//	@Autowired
//	@Qualifier("redisTemplate")
//	private RedisTemplate redisTemplate;
	@Autowired
	private Environment environment;
	@Autowired
	private Config config;
	public  void prepareRedis() {
		HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
		stringRedisTemplate.delete("HW:BIND:178:17826828164");
		ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();
		String poolkey="SHCC:010:3";
		hash.put("phonelockmap",poolkey,"1");
		Long size = opsForList.size(poolkey);
		if(size==0)
		opsForList.leftPush(poolkey,"17826828164");
		//SHCC:10:3
		//phonelockmap

	}
//	public  void prepareRedis() {
//		HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
//		stringRedisTemplate.delete("HW:BIND:178:17826828164");
//		ListOperations<String, String> opsForList = stringRedisTemplate.opsForList();
//		String poolkey="SHCC:010:3";
//		hash.put("phonelockmap",poolkey,"1");
//		Long size = opsForList.size(poolkey);
//		if(size==0)
//			opsForList.leftPush(poolkey,"17826828164");
//		//SHCC:10:3
//		//phonelockmap
//
//	}
}
