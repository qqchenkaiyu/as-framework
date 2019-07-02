package cn.witsky.bs.basicframework.utils;


import cn.witsky.bs.config.Config;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * Created by Administrator on 2019/4/9.
 */
@Component
public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private RestTemplate restTemplate;
	private RetryTemplate retryTemplate;
	private Config config;
	private final ThreadPoolTaskExecutor taskExecutor;
	private static TypeReference<Map<String, Object>> TYPE_REFERENCE_FOR_STRING_KEYED_MAP = new TypeReference<Map<String, Object>>() {
	};

@Autowired
	public HttpUtil(RestTemplate restTemplate, RetryTemplate retryTemplate, Config config, @Qualifier("internalHttp") ThreadPoolTaskExecutor taskExecutor) {
		this.restTemplate=restTemplate;
		this.retryTemplate=retryTemplate;
		this.config=config;
		this.taskExecutor=taskExecutor;

	}
	public  String postDataForString(String Url, Object Data, Map<String, String> hearder) throws Exception {

		ResponseEntity<String> responseEntity = retryTemplate.execute(retryContext->{
				HttpEntity<String> httpEntityForJson = createHttpEntityForJson(JSON.toJSONString
						(Data),hearder);
				ResponseEntity<String> exchange = restTemplate.exchange(Url, HttpMethod.POST, httpEntityForJson, String.class);
				return exchange;
		}, retryContext-> {
				logger.error("多次访问失败,retryContext={},url={}", retryContext,Url);
				return null;
		});
		if (responseEntity != null) {
			HttpStatus httpStatus = responseEntity.getStatusCode();
			if (httpStatus != null && httpStatus == HttpStatus.OK) {
				return responseEntity.getBody();
			}
		}
		throw new Exception("请求失去响应！！");

	}

	public static HttpEntity<String> createHttpEntityForJson(String jsonString,
															 Map<String, String> headers) {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		if (headers != null) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpHeaders.set(entry.getKey(), entry.getValue());
			}
		}
		return new HttpEntity<String>(jsonString, httpHeaders);
	}
}
