package com.ben.icebergdataadaptor.job

import com.ben.icebergdataadaptor.infra.service.StockHistoryDownloadService
import com.ben.icebergdataadaptor.persistence.service.StockHistoryPersistenceService
import com.ben.icebergdataadaptor.persistence.service.StockInfoPersistenceService
import com.google.common.util.concurrent.RateLimiter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class IcebergJob(
	val stockInfoPersistence: StockInfoPersistenceService,
	val stockHistoryPersistence: StockHistoryPersistenceService,
	val downloadService: StockHistoryDownloadService
) {
	@Scheduled(cron = "0 30 18,21,23 * * ?")
	fun downloadAndPersistenceStockHistory() {
		logger.info("start job to download stock history start = {}", LocalDateTime.now())
		try {
			val currentDay = LocalDate.now().toString()
			val rateLimiter = RateLimiter.create(5.0)
			val stockList = stockInfoPersistence.queryAll()?.sortedByDescending { it.haveDataTimes }
			stockList?.let {
				for (info in it) {
					val stock = "${info.exchangeHouse}.${info.stockNo}"
					val newestDate = stockHistoryPersistence.queryTheNewestDay(stock, info.stockNo)
//					logger.info("newestDate = $newestDate and currentDay = $currentDay")
					if (newestDate == currentDay) {
						continue
					}
					val startDay = getTheStartDay(newestDate)
					if (startDay > currentDay) {
						continue
					}
					if (startDay == currentDay && LocalDateTime.now() < LocalDateTime.parse(
							"${LocalDate.now()} 18:00:00",
							DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
						)
					) continue
//					if (rateLimiter.tryAcquire()) {
//						logger.info("startDay $startDay, endDay $currentDay")
						downloadService.stockHistory(stock, startDay, currentDay)
//					}
				}
			}
		} catch (e: Exception) {
			logger.error("#downloadAndPersistenceStockHistory error", e)
		}
		logger.info("job is end = {}", LocalDateTime.now())
	}
	
	private fun getTheStartDay(theNewestDate: String?): String {
		return theNewestDate?.let {
			LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1).toString()
		} ?: THE_ANCIENT
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(IcebergJob::class.java)
		const val THE_ANCIENT = "2000-01-01"
	}
}