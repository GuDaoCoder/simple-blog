package com.github.common.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author Gudao
 * @since 2024/7/31
 */
@Configuration
public class ThreadPoolConfig {

	/**
	 * 核心线程数 = cpu 核心数 + 1
	 */
	private final int core = Runtime.getRuntime().availableProcessors() + 1;

	@Bean(name = "threadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(core);
		executor.setMaxPoolSize(core * 2);
		executor.setQueueCapacity(128);
		executor.setKeepAliveSeconds(300);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return TtlExecutors.getTtlExecutor(executor);
	}

}
