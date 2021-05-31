package com.ben.analysis.job

import com.ben.analysis.persistence.repository.StockWordsRepository
import com.ben.analysis.service.StockWordsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StockWordsJob(val stockWordsService: StockWordsService, val stockWordsRepository: StockWordsRepository) {

    @Scheduled(cron = "#{@stockWordsCron}")
    fun stockWords() {
        logger.info("#stockWords start ${LocalDateTime.now()}")
        stockWordsService.stockWords()
        logger.info("#stockWords end ${LocalDateTime.now()}")
    }

    @Scheduled(cron = "#{@stockWordsValidateCron}")
    fun stockWordsValidate() {
        logger.info("#stockWordsValidate start ${LocalDateTime.now()}")
        try {
            val stockWords = stockWordsRepository.findAll().filter { it.words_is_valid && !it.is_st }
            if (stockWords !=null && stockWords.isNotEmpty())
                stockWords.forEach{
                    stockWordsService.checkWordsValid(it)
                }
        } catch (e: Exception) {
            logger.info("#stockWordsValidate error:", e)
        }
        logger.info("#stockWordsValidate end ${LocalDateTime.now()}")
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(StockWordsJob::class.java)
    }
}