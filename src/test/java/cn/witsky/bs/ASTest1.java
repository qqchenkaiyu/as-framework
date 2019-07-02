package cn.witsky.bs;

import cn.witsky.bs.basicframework.utils.BasicUtil;
import cn.witsky.bs.config.Config;
import cn.witsky.bs.domain.Extra;
import cn.witsky.bs.ndfjr.as.aliax.FreeMarkerUtil;
import cn.witsky.bs.ndfjr.as.aliax.PrepareDataUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Wangxb on 2018/6/6.
 */


public class ASTest1 {

    @Autowired
//	@Qualifier("restTemplateAsync")
    private RestTemplate restTemplate;
//	@Autowired
//	private StringRedisTemplate redisTemplate;
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate redisTemplate;
	@Autowired
	private PrepareDataUtil prepareDataUtil;
    @Autowired
    private Environment environment;
	@Autowired
	private Config config;
    String urlPrefix="http://192.168.110.94:6051";
	private final static Logger logger = LoggerFactory.getLogger(ASTest1.class);
	@Test
	public void bindTest(){
		Extra extra = new Extra("0","0","0","0","0","0");

		Extra extra1 = new Extra("1",null,null,null,null,null);
		logger.info(JSONObject.toJSONString(extra));
		BasicUtil.copyProperties(extra1,extra);
		logger.info(JSONObject.toJSONString(extra));
	}


	@Test
	public void unbindTest(){
		String bindid="2019061319415108081607041041";
		prepareDataUtil.prepareRedis();
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("method","query_call_control");
		hashMap.put("recordType","CALL");
		String str= FreeMarkerUtil.geneFileStr("unbind.flt","src/test/resources/template",null);
		MultiValueMap<String, String> headers= new LinkedMultiValueMap<>(4);
		JSONObject parse = (JSONObject) JSONObject.parse(str);
		//headers.set(Cons.HeaderFiled.LOCAL_SESSION_ID,"33032215565656");
		headers.set("appkey","huawei");
		headers.set("ts","19960106121011111");
		headers.set("msgdgt","E2BE8FD97DE959205ADD9713E90447CB");
		HttpEntity<Object> entity = new HttpEntity<Object>(parse, headers);
		String url = urlPrefix + "/v2/axb/"+bindid;
		ResponseEntity<String> objectResponseEntity = restTemplate.exchange(url,
		HttpMethod.DELETE,entity,String.class);
		logger.info(objectResponseEntity.getBody().toString());
		Scanner scanner = new Scanner(System.in);
		while (!scanner.next().equals("ok")) {

		}


	}
	//测试redis管道
	//速度提升?  肯定的  随便看一下
	//事务控制？  增删改？
	@Test
	public void  nopiplelineTest(){
		HashOperations<String, Object, Object> forHash = redisTemplate.opsForHash();
		forHash.put("testkey","init","i");
		int number=10000;
		long start=System.currentTimeMillis();
		for (int i = 0; i < number; i++) {
			forHash.put("test找那个key",i+"","i");
		}
		long end=System.currentTimeMillis();
		logger.info("不用管道插入"+number+"次, 耗时"+(end-start)+"ms");
	}
	//测试redis管道
	//速度提升?  肯定的  随便看一下
	//事务控制？  增删改？
	@Test
	public void  piplelineTest(){
		HashOperations<String, Object, Object> forHash = redisTemplate.opsForHash();
		forHash.put("testkey","init","i");
		int number=10000;



		long start1=System.currentTimeMillis();
		//pipeline
		List<Object> results = (List<Object>)redisTemplate.execute((RedisConnection connection)->{
			connection.openPipeline();
			for (int i = 0; i < number; i++) {
				try {
					forHash.put("中文key",i+"","i");
					//connection.hSet("中文key".getBytes("UTF-8"),(i+"").getBytes("UTF-8"),"i".getBytes("UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return connection.closePipeline();
		});
		long end1=System.currentTimeMillis();
		logger.info("用管道插入"+number+"次, 耗时"+(end1-start1)+"ms");
	}
	//测试redis管道  换一种代码风格

	@Test
	public void  piplelineTest2(){
		HashOperations<String, Object, Object> forHash = redisTemplate.opsForHash();
		forHash.put("testkey","init","i");
		int number=10000;



		long start1=System.currentTimeMillis();
		//pipeline
		List<Object> results = (List<Object>)redisTemplate.executePipelined((RedisConnection connection)->{
			for (int i = 0; i < number; i++) {
				try {
					connection.hSet("中文key".getBytes("UTF-8"),(i+"").getBytes("UTF-8"),"i".getBytes("UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return  null;
		});
		long end1=System.currentTimeMillis();
		logger.info("用管道插入"+number+"次, 耗时"+(end1-start1)+"ms");
		logger.info(results.size()+"");
	}
    @Test
    public void callpushTest(){
		prepareDataUtil.prepareRedis();
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("method","push_call_release");
		hashMap.put("callType","CALL");
        String str= FreeMarkerUtil.geneFileStr("callfinish.flt","src/test/resources/template",hashMap);
			MultiValueMap<String, String> headers= new LinkedMultiValueMap<>(4);
			JSONObject parse = (JSONObject) JSONObject.parse(str);
			//headers.set(Cons.HeaderFiled.LOCAL_SESSION_ID,"v2");
			headers.set("witsky-ccf-id","name=ALICCF2");
			HttpEntity<Object> entity = new HttpEntity<Object>(parse, headers);
			String s = urlPrefix + "/control";
			Object object = restTemplate.postForEntity(s, entity, Object.class);
			System.out.println(object);
		Scanner scanner = new Scanner(System.in);
		while (!scanner.next().equals("ok")) {

		}


    }
	@Test
	public void smspushTest(){
		prepareDataUtil.prepareRedis();
		String str= FreeMarkerUtil.geneFileStr("smsfinish.flt","src/test/resources/template",null);
		MultiValueMap<String, String> headers= new LinkedMultiValueMap<>(4);
		JSONObject parse = (JSONObject) JSONObject.parse(str);
		headers.set("witsky-api-version","v2");
		headers.set("witsky-ccf-id","name=ALICCF2");
		HttpEntity<Object> entity = new HttpEntity<Object>(parse, headers);
		String s = urlPrefix + "/smEvtRecordPush";
		Object object = restTemplate.postForEntity(s, entity, Object.class);
		System.out.println(object);
		Scanner scanner = new Scanner(System.in);
		while (!scanner.next().equals("ok")) {

		}


	}
	@Test
	public void unbindpushTest(){
		prepareDataUtil.prepareRedis();
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("method","push_subs_event");
		hashMap.put("event","unbind");
		hashMap.put("status","0");
		String str= FreeMarkerUtil.geneFileStr("unbind.flt","src/test/resources/template",hashMap);
		MultiValueMap<String, String> headers= new LinkedMultiValueMap<>(4);
		JSONObject parse = (JSONObject) JSONObject.parse(str);
		headers.set("witsky-api-version","v2");
		headers.set("witsky-ccf-id","name=ALICCF2");
		HttpEntity<Object> entity = new HttpEntity<Object>(parse, headers);
		String s = urlPrefix + "/control";
		Object object = restTemplate.postForEntity(s, entity, Object.class);
		System.out.println(object);
		Scanner scanner = new Scanner(System.in);
		while (!scanner.next().equals("ok")) {

		}


	}
//	@Test
//	public void parseTest() {
//		CallPush callPush = new CallPush();
//		CallPush callPush1 = JSONObject.parseObject(JSONObject.toJSONString(callPush), CallPush.class);
//	}
}
