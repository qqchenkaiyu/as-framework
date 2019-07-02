package cn.witsky.bs.basicframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.concurrent.Callable;

/**
 * @author: chenkaiyu
 * @create: 2019-06-18 15:15
 */
@Component
public class AsyncUtil {private static Logger logger = LoggerFactory.getLogger(AsyncUtil.class);

	@Autowired
	@Qualifier("internalHttp")
	private ThreadPoolTaskExecutor taskExecutor;
	public ListenableFuture async(Callable runnable, SuccessCallback successCallback) {
		ListenableFuture async = taskExecutor.submitListenable(runnable);
		async.addCallback(successCallback,ex->{
			BasicUtil.handleErrorOutput(logger,ex);});
		return async;
	}
}
