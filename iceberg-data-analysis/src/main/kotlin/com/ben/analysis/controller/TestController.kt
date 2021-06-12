package com.ben.analysis.controller

import com.ben.analysis.job.StockWordsJob
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/test")
class TestController (val stockWordsJob: StockWordsJob) {
	@GetMapping("/words")
	fun words() {
		logger.info("TestController#wrods start current is ${LocalDateTime.now()}")
		stockWordsJob.stockWords()
		logger.info("TestController#wrods end at ${LocalDateTime.now()}")
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(TestController::class.java)
	}
}
