package com.ben.icebergdataadaptor.job

import com.ben.icebergdataadaptor.infra.service.ProfitQuarterDownloadService
import com.ben.icebergdataadaptor.persistence.service.QuarterProfitPersistenceService
import com.ben.icebergdataadaptor.persistence.service.StockInfoPersistenceService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ProfitJob(
	val profitQuarterDownloadService: ProfitQuarterDownloadService,
	val stockInfoService: StockInfoPersistenceService,
	val profitPersistenceService: QuarterProfitPersistenceService
) {
	
	@Scheduled(cron = "#{@profitCron}")
	fun profit() {
		logger.info("ProfitJob start ${LocalDateTime.now()}")
		val stocks = stockInfoService.queryAll()?.filter { it.type == "1" } ?: return
		for (info in stocks) {
			try {
				val profits = info.codeWithEx?.let { profitPersistenceService.queryByStock(it) } ?: continue
				val maxYear: String? =
					profits.maxWithOrNull(compareBy { it.statDate?.substring(0, 4)?.toInt() })?.statDate?.substring(
						0,
						4
					)
				val year = maxYear?.toInt()?.plus(1) ?: info.ipoDate?.substring(0, 4)?.toInt() ?: 2000
				val currentYear = LocalDate.now().year
				for (y in year..currentYear) {
					profitQuarterDownloadService.profitQuarter(
						info.codeWithEx.orEmpty(),
						y.toString(),
						ProfitQuarterDownloadService.Quarter.ONE
					)
					profitQuarterDownloadService.profitQuarter(
						info.codeWithEx.orEmpty(),
						y.toString(),
						ProfitQuarterDownloadService.Quarter.TWO
					)
					profitQuarterDownloadService.profitQuarter(
						info.codeWithEx.orEmpty(),
						y.toString(),
						ProfitQuarterDownloadService.Quarter.THREE
					)
					profitQuarterDownloadService.profitQuarter(
						info.codeWithEx.orEmpty(),
						y.toString(),
						ProfitQuarterDownloadService.Quarter.FOUR
					)
				}
			} catch (e: Exception) {
				logger.error("ProfitJob#profit error", e)
			}
		}
		logger.info("ProfitJob end ${LocalDateTime.now()}")
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(ProfitJob::class.java)
	}
}