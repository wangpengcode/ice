package com.ben.analysis.controller

import com.ben.analysis.job.LastDaysBlowShadowJob
import com.ben.analysis.job.LastDaysBlowStrongJob
import com.ben.analysis.job.StockWordsJob
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/test")
class TestController(
        val stockWordsJob: StockWordsJob,
        val shadowJob: LastDaysBlowShadowJob,
        val strongJob: LastDaysBlowStrongJob) {
    @GetMapping("/words")
    fun words() {
        logger.info("TestController#wrods start current is ${LocalDateTime.now()}")
        stockWordsJob.stockWords()
        logger.info("TestController#wrods end at ${LocalDateTime.now()}")
    }

    @GetMapping("/valid/words")
    fun stockWordsValidate() {
        logger.info("TestController#stockWordsValidate start current is ${LocalDateTime.now()}")
        stockWordsJob.stockWordsValidate()
        logger.info("TestController#stockWordsValidate end at ${LocalDateTime.now()}")
    }

    @GetMapping("/shadow")
    fun shadowJob() {
        logger.info("TestController#shadowJob start current is ${LocalDateTime.now()}")
        shadowJob.shadowJob()
        logger.info("TestController#shadowJob end at ${LocalDateTime.now()}")
    }

    @GetMapping("/strong")
    fun strongJob() {
        logger.info("TestController#strongJob start current is ${LocalDateTime.now()}")
        strongJob.strongJob()
        logger.info("TestController#strongJob end at ${LocalDateTime.now()}")
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TestController::class.java)
    }
}
