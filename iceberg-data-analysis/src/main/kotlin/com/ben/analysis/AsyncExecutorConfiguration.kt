package com.ben.analysis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@ConfigurationProperties(prefix = "async-task")
class AsyncExecutorConfiguration {
	
	var corePoolSize: Int = 20
	
	var maximumPoolSize: Int = 40
	
	var keepAliveSeconds: Int = 300
	
	var queueCapacity: Int = 50
	
	var threadNamePrefix: String = "Async-Task-"
	
	var awaitTerminationTime: Int = 60
	
	var waitTasksComplete: Boolean = true
	
	@Bean
	fun asyncExecutor(): Executor {
		val executor = ThreadPoolTaskExecutor()
		executor.corePoolSize = corePoolSize
		executor.maxPoolSize = maximumPoolSize
		executor.keepAliveSeconds = keepAliveSeconds
		executor.setQueueCapacity(queueCapacity)
		executor.setThreadNamePrefix(threadNamePrefix)
		executor.setAwaitTerminationSeconds(awaitTerminationTime)
		executor.setWaitForTasksToCompleteOnShutdown(waitTasksComplete)
		return executor
	}
}