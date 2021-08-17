package com.example.chh.wx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * @author apple
 */
@Configuration
public class ThreadPoolConfig {
    @Bean("AsyncTaskExecutor")
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心数
        executor.setCorePoolSize(4);
        //最大线程数
        executor.setMaxPoolSize(8);
        //队列容量
        executor.setQueueCapacity(16);
        //线程活跃时间
        executor.setKeepAliveSeconds(60);
        //默认线程名称
        executor.setThreadNamePrefix("task-");
        //拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        return executor;
    }
}
