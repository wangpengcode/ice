package com.ben.analysis.job

import com.ben.analysis.service.StockWordsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StockWordsJob ( val stockWordsService: StockWordsService){

    @Scheduled(cron = "#{@stockWordsCron}")
    fun stockWords() {
        logger.info("#stockWords start ${LocalDateTime.now()}")
        stockWordsService.stockWords()
        logger.info("#stockWords end ${LocalDateTime.now()}")
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(StockWordsJob::class.java)
    }
}