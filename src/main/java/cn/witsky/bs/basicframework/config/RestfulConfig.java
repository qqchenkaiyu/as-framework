package cn.witsky.bs.basicframework.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author HuangYX
 * @date 2018/6/21 15:32
 */
@Component
public class RestfulConfig {

    private static final Logger logger = LoggerFactory.getLogger(RestfulConfig.class);

    private ClientHttpRequestFactory getClientHttpRequestFactory(RequestConfig requestConfig, SocketConfig socketConfig) {
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setDefaultSocketConfig(socketConfig)
                .build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }

    @Bean(name = "restTemplate")
    public RestTemplate createRestTemplateForInternal() {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000)
                .setConnectTimeout(2000).setConnectionRequestTimeout(2000).build();
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoLinger(0).setSoKeepAlive(false).build();

        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory(requestConfig, socketConfig));
        setStringHttpMessageConverterCharset(restTemplate, "utf-8");
        return restTemplate;
    }
    @Bean(name = "internalHttp")
    public ThreadPoolTaskExecutor createThreadPoolExecutorForInternalHttp() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(500);
        executor.setMaxPoolSize(500);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("internalHttp-pool-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
    @Bean
    public RetryTemplate createRetryTemplate() {
        RetryTemplate r = new RetryTemplate();
        r.setBackOffPolicy(new ExponentialBackOffPolicy());
        SimpleRetryPolicy policy = new SimpleRetryPolicy(2);
        r.setRetryPolicy(policy);
        return r;
    }
    private void setStringHttpMessageConverterCharset(RestTemplate restTemplate, String charset) {
        // 找出并修改默认的StringHttpMessageConverter
        // 关闭Accept-Charset的输出（防止输出超长的编码列表）
        // 设置默认编码为UTF-8
        boolean stringConverterFound = false;
        for (HttpMessageConverter httpMessageConverter : restTemplate.getMessageConverters()) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                StringHttpMessageConverter stringHttpMessageConverter = (StringHttpMessageConverter) httpMessageConverter;
                stringHttpMessageConverter.setWriteAcceptCharset(false);
                stringHttpMessageConverter.setDefaultCharset(Charset.forName(charset));
                stringConverterFound = true;
                break;
            }
        }
        if (!stringConverterFound) {
            // 如果不存在StringHttpMessageConverter，则创建一个
            StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName(charset));
            stringHttpMessageConverter.setWriteAcceptCharset(false);
            restTemplate.getMessageConverters().add(stringHttpMessageConverter);
        }
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMediaTypes.add(MediaType.TEXT_PLAIN);
        fastMediaTypes.add(MediaType.TEXT_HTML);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        messageConverters.add(fastConverter);
    }
    //使用fastjson
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 1.定义一个converters转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 3.在converter中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 4.将converter赋值给HttpMessageConverter
        HttpMessageConverter<?> converter = fastConverter;
        // 5.返回HttpMessageConverters对象
        return new HttpMessageConverters(converter);
    }
}
