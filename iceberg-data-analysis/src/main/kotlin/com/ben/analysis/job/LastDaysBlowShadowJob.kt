package com.ben.analysis.job

import com.ben.analysis.extensions.isBefore
import com.ben.analysis.extensions.isBlowShadow
import com.ben.analysis.persistence.entity.StockInfo
import com.ben.analysis.persistence.service.StockHistoryPersistenceService
import com.ben.analysis.persistence.service.StockInfoPersistenceService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class LastDaysBlowShadowJob(val stockInfo: StockInfoPersistenceService, val history: StockHistoryPersistenceService) {
    @Scheduled(cron = "#{@lastDaysBlowShadowCron}")
    fun shadowJob() {
        logger.info("#shadowJob start at ${LocalDateTime.now()}")
        val allStocks = stockInfo.queryAll()
        val localDate = LocalDate.now().toString()
        if (allStocks != null && allStocks.isNotEmpty()) {
            var size = allStocks.size
            for (stock in allStocks) {
                try {
                    if (stock.lastDaysBlowShadow15 != null
                            && stock.lastDaysBlowShadow15?.isBefore(localDate) == false) {
                        size--
                        continue
                    }
                    stock.codeWithEx?.let { stockInfo.save(daysBlow(it, stock)) }
                    size--
                    logger.info("#shadowJob ${stock.codeWithEx} is already done! $size is waiting to done!")
                } catch (e: Exception) {
                    logger.error("#shadowJob ${stock.codeWithEx} error: ", e)
                }
            }
        }
        logger.info("#shadowJob end at ${LocalDateTime.now()}")
    }

    private fun daysBlow(stockNo: String, info: StockInfo): StockInfo {
        val localDate = LocalDate.now().toString()
        val histories = history.queryStocks(stockNo)
        if (histories == null || histories.size < 102) {
            info.apply {
                lastDaysBlowShadow100 = "$localDate-1"
                lastDaysBlowShadow50 = "$localDate-1"
                lastDaysBlowShadow15 = "$localDate-1"
            }
            return info
        }
        val last100 = histories.sortedByDescending { it.date }.subList(0, 100)
        var last15Shadows = 0
        var last50Shadows = 0
        var last100Shadows = 0
        var i = 0
        for (history in last100) {
            val currentIsShadow = isBlowShadow(
                    low = history.low?.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                    close = history.close.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                    open = history.open.toBigDecimalOrNull() ?: BigDecimal.ZERO)
            if (i <= 15 && currentIsShadow) {
                last15Shadows++
            }

            if (i <= 50 && currentIsShadow) {
                last50Shadows++
            }

            if (i <= 100 && currentIsShadow) {
                last100Shadows++
            }
            i++
        }
        info.apply {
            lastDaysBlowShadow100 = "$localDate-$last100Shadows"
            lastDaysBlowShadow50 = "$localDate-$last50Shadows"
            lastDaysBlowShadow15 = "$localDate-$last15Shadows"
        }
        return info
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(LastDaysBlowShadowJob::class.java)
    }
}