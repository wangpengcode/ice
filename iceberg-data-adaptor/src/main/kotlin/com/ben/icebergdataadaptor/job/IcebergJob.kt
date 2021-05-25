package com.ben.icebergdataadaptor.job

import com.ben.icebergdataadaptor.infra.service.StockHistoryDownloadService
import com.ben.icebergdataadaptor.persistence.service.StockHistoryPersistenceService
import com.ben.icebergdataadaptor.persistence.service.StockInfoPersistenceService
import com.google.common.util.concurrent.RateLimiter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class IcebergJob(
	val stockInfoPersistence: StockInfoPersistenceService,
	val stockHistoryPersistence: StockHistoryPersistenceService,
	val downloadService: StockHistoryDownloadService
) {
	@Scheduled(cron = "0 13 18,19,21 * * ?")
	fun downloadAndPersistenceStockHistory() {
		logger.info("start job to download stock history start = {}", LocalDate.now())
		try {
			val currentDay = LocalDate.now().toString()
			val rateLimiter = RateLimiter.create(3.0)
			stockInfoPersistence.queryAll()?.let {
				for (info in it) {
					val stock = "${info.exchangeHouse}.${info.stockNo}"
					val newestDate = stockHistoryPersistence.queryTheNewestDay(stock)
					if (newestDate == currentDay) {
						continue
					}
					val startDay = getTheStartDay(newestDate)
					if (rateLimiter.tryAcquire()) {
						downloadService.stockHistory(stock, startDay, currentDay)
					}
				}
			}
		} catch (e: Exception) {
			logger.error("#downloadAndPersistenceStockHistory error", e)
		}
		logger.info("job is end = {}", LocalDateTime.now())
	}
	
	private fun getTheStartDay(theNewestDate: String?): String {
		return theNewestDate?.let { it } ?: THE_ANCIENT
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(IcebergJob::class.java)
		const val THE_ANCIENT = "2000-01-01"
	}
}