package com.ben.icebergdataadaptor

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "schedule.job")
class Configurations {
	var history: String = " 0 38 17,21,8 * * ?"
	var profit: String = "0 0 23 * * ?"
	
	@Bean
	fun historyCron(): String{
		return history
	}
	
	@Bean
	fun profitCron(): String{
		return profit
	}
}