package cn.witsky.bs.basicframework.multiThread;

import cn.witsky.bs.basicframework.utils.BasicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


@Configuration
@EnableAsync

public class ExecutorConfig {
  private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

  /**
   * Set the ThreadPoolExecutor'submit core pool size.
   */
  @Value("${witsky.async-thread-pool-core-size:100}")
  private int corePoolSize;
  /**
   * Set the ThreadPoolExecutor'submit maximum pool size.
   */
  @Value("${witsky.async-thread-pool-max-size:100}")
  private int maxPoolSize;
  /**
   * Set the capacity for the ThreadPoolExecutor'submit BlockingQueue.
   */
  @Value("${witsky.async-thread-pool-queue-capacity:1000}")
  private int queueCapacity;

//  @Bean("statemachineList")
//  public ThreadPoolTaskExecutor taskCreator() {
//    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//    executor.setCorePoolSize(corePoolSize);
//    executor.setMaxPoolSize(maxPoolSize);
//    executor.setQueueCapacity(queueCapacity);
//    executor.setThreadNamePrefix("statemachineList-pool-");
//    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//    executor.initialize();
//    ThreadPoolExecutor threadPoolExecutor = executor.getThreadPoolExecutor();
//    threadPoolExecutor.prestartAllCoreThreads();
//    return executor;
//  }

  @Bean(name = "internalHttp")
  public ThreadPoolTaskExecutor createThreadPoolExecutorForInternalHttp() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSize);
    executor.setMaxPoolSize(corePoolSize);
    executor.setQueueCapacity(1000);
    executor.setThreadNamePrefix("internalHttp-pool-");
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
    return executor;
  }

  @Bean(name = "asyncConfigurer")
  public AsyncConfigurer asyncConfigurer() {
    return new AsyncConfigurer(){
      @Override
      public Executor getAsyncExecutor() {
        return null;
      }
      @Override
      public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex,method,params)->{
          BasicUtil.handleErrorOutput(logger,ex);

        };
      }
    };
  }

}
