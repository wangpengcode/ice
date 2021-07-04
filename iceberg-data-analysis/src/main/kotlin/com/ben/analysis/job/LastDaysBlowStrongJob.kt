package com.ben.analysis.job

import com.ben.analysis.extensions.isBefore
import com.ben.analysis.persistence.entity.StockHistory
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
class LastDaysBlowStrongJob(val stockInfo: StockInfoPersistenceService, val history: StockHistoryPersistenceService) {
    @Scheduled(cron = "#{@lastDaysStrongCron}")
    fun strongJob() {
        logger.info("#strongJob start at ${LocalDateTime.now()}")
        val allStocks = stockInfo.queryAll()
        val localDate = LocalDate.now().toString()

        if (allStocks != null && allStocks.isNotEmpty()) {
            var size = allStocks.size
            for (stock in allStocks) {
                try {
                    if (stock.lastDaysStrong5 != null && stock.lastDaysStrong5?.isBefore(localDate) == false) {
                        size--
                        continue
                    }
                    stock.codeWithEx?.let { stockInfo.save(daysBlow(it, stock)) }
                    size--
                    logger.info("#strongJob ${stock.codeWithEx} is already done! $size is waiting to done!")
                } catch (e: Exception) {
                    logger.error("#strongJob ${stock.codeWithEx} error:", e)
                }
            }
        }
        logger.info("#strongJob end at ${LocalDateTime.now()}")
    }

    private fun daysBlow(stockNo: String, info: StockInfo): StockInfo {
        val localDate = LocalDate.now().toString()
        val histories = history.queryStocks(stockNo)
        if (histories == null || histories.size < 100) {
            info.apply {
                lastDaysStrong20 = "$localDate-1"
                lastDaysStrong10 = "$localDate-1"
                lastDaysStrong5 = "$localDate-1"
            }
            return info
        }
        val lastDay5 = histories.sortedByDescending { it.date }.subList(0, 5)
        info.apply {
            lastDaysStrong5 = "$localDate-"+strongDate(lastDay5)
        }
        val lastDay10 = histories.sortedByDescending { it.date }.subList(0, 10)
        info.apply {
            lastDaysStrong10 = "$localDate-"+strongDate(lastDay10)
        }
        val lastDay20 = histories.sortedByDescending { it.date }.subList(0, 20)
        info.apply {
            lastDaysStrong20 = "$localDate-"+strongDate(lastDay20)
        }
        return info
    }

    private fun strongDate(histories: List<StockHistory>): String {
        var date = "-1"
        if (histories.size < 3) return date
        var i = 0
        do {
            val change = histories[i].pctChg?.toBigDecimalOrNull()
                    ?.add(histories[i + 1].pctChg?.toBigDecimalOrNull() ?: BigDecimal.ZERO)
                    ?.add(histories[i + 2].pctChg?.toBigDecimalOrNull() ?: BigDecimal.ZERO) ?: BigDecimal.ZERO
            if (change < BigDecimal("20")) {
                i++
                continue
            }
            var turn = histories[i].turn?.toBigDecimalOrNull()
                    ?.add(histories[i + 1].turn?.toBigDecimalOrNull() ?: BigDecimal.ZERO)
                    ?.add(histories[i + 2].turn?.toBigDecimalOrNull() ?: BigDecimal.ZERO) ?: BigDecimal.ZERO
            if (turn <= BigDecimal.TEN) {
                date = histories[i + 2].date
                break
            }
            i++
        } while (i + 2 < histories.size)
        return date
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(LastDaysBlowStrongJob::class.java)
    }
}