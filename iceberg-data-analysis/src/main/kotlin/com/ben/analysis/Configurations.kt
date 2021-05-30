package com.ben.analysis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "schedule.job")
class Configurations {
	var superstar: String = " 0 38 17,21,8 * * ?"
	var stockwords: String = " 0 38 17,21,8 * * ?"

	@Bean
	fun superStarCron(): String{
		return superstar
	}

	@Bean
	fun stockWordsCron(): String{
		return stockwords
	}
}