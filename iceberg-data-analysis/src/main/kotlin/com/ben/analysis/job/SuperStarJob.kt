package com.ben.analysis.job

import com.ben.analysis.persistence.repository.StockInfoRepository
import com.ben.analysis.service.SuperStartService
import com.google.common.util.concurrent.RateLimiter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SuperStarJob(
        val superStartService: SuperStartService,
        val stockInfoRepository: StockInfoRepository
) {

    @Scheduled(cron = "#{@superStarCron}")
    fun startSuperStar() {
        logger.info("#startSuperStar start ${LocalDateTime.now()}")
//        val rateLimiter = RateLimiter.create(2.0)
        stockInfoRepository.findStocks()?.let { it ->
            for (it2 in it) {
                try {
                    it2.codeWithEx?.let { it3 ->
                        superStartService.superStar(it3)
                        Thread.sleep(200)
                    }
                } catch (e: Exception) {
                    logger.error("#startSuperStar error", e)
                    continue
                }
            }
        }
        logger.info("#startSuperStar end ${LocalDateTime.now()}")
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SuperStarJob::class.java)
    }
}