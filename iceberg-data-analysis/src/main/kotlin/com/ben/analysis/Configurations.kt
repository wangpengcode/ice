package com.ben.analysis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "schedule.job")
class Configurations {
	var superstar: String = " 0 38 17,21,8 * * ?"
	var stockwords: String = " 0 38 17,21,8 * * ?"
	var stockwordvalidate: String = " 0 38 17,21,8 * * ?"
	var lastdaysblowshadowjob: String = " 0 50 20,21,22 * * ?"
	var lastdaysstrong: String = " 0 58 20,21,22 * * ?"

	@Bean
	fun superStarCron(): String{
		return superstar
	}

	@Bean
	fun stockWordsCron(): String{
		return stockwords
	}

	@Bean
	fun stockWordsValidateCron(): String{
		return stockwordvalidate
	}

	@Bean
	fun lastDaysBlowShadowCron(): String{
		return lastdaysblowshadowjob
	}

	@Bean
	fun lastDaysStrongCron(): String{
		return lastdaysstrong
	}
}