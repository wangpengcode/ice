package com.ben.icebergdataadaptor.webhook

import com.ben.icebergdataadaptor.extensions.*
import com.ben.icebergdataadaptor.persistence.entity.StockHistory
import com.ben.icebergdataadaptor.persistence.entity.StockInfo
import com.ben.icebergdataadaptor.persistence.service.QuarterProfitPersistenceService
import com.ben.icebergdataadaptor.persistence.service.StockHistoryPersistenceService
import com.ben.icebergdataadaptor.persistence.service.StockInfoPersistenceService
import com.ben.icebergdataadaptor.transfer.toIndustry
import com.ben.icebergdataadaptor.transfer.toProfitQuarter
import com.ben.icebergdataadaptor.transfer.toStockHistory
import com.ben.icebergdataadaptor.transfer.toStockInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.LocalDate

@RestController
@RequestMapping("/file/upload")
class ReceiveController(
	val stockInfoPersistenceService: StockInfoPersistenceService,
	val stockHistoryPersistenceService: StockHistoryPersistenceService,
	val profitService: QuarterProfitPersistenceService
) {
	data class Industry(
		val code: String,
		val date: String,
		val codeName: String,
		val industry: String,
		val industryClassify: String,
		var nakedCode: String? = null
	)
	
	@PostMapping("/industry")
	fun industry(@RequestBody list: String) {
		val industrys = list.split("],")
		val industryList = mutableListOf<Industry>()
		for (s in industrys) {
			try {
				val detail = s.split(",")
				val industry = detail.toIndustry().apply {
					this.nakedCode = code.toNakedCode()
				}
				industryList.add(industry)
			} catch (e: Exception) {
				continue
			}
		}
		val mappingData = mutableListOf<Industry>()
		stockInfoPersistenceService.queryAll()?.let {
			for (stock in it) {
				for (i in industryList) {
					if (i.nakedCode == stock.stockNo) {
						mappingData.add(i)
						stock.apply {
							exchangeHouse = i.code.split(".")[0].removeIllegalChar()
							codeWithEx = i.code
							codeName = i.codeName
							lastUpdateDate = i.date
							codeWithEx = i.code
							industry = i.industry
							industryClassification = i.industryClassify
						}
						stockInfoPersistenceService.save(stock)
						logger.info("stock $stock")
						break
					}
				}
			}
		}
		val more = industryList - mappingData
		try {
			val moreList = mutableListOf<StockInfo>()
			for (m in more) {
				moreList.add(m.toStockInfo())
			}
			moreList.chunked(20).forEach {
				stockInfoPersistenceService.saveAll(it)
			}
		} catch (e: Exception) {
			logger.error("have mor industry don't save ${more}")
		}
		logger.info("industrys = $industryList")
	}
	
	@PostMapping("/all")
	fun uploadAllStock(@RequestBody list: String) {
		val a = list.split("],")
		val c = mutableListOf<StockInfo>()
		for (it in a) {
			try {
				val currentItem = it.split(",")
				val info = currentItem.toStockInfo()
				logger.info("uploadAllStock ${info}")
				c.add(info)
				if (c.size == 100) {
					stockInfoPersistenceService.saveAll(c)
					c.clear()
				}
			} catch (e: Exception) {
				stockInfoPersistenceService.saveAll(c)
				c.clear()
				logger.error("error ", e)
				continue
			}
		}
		if (list.isNotEmpty())
			stockInfoPersistenceService.saveAll(c)
	}
	
	@PostMapping("/history")
	@Async("asyncExecutor")
	fun history(@RequestBody list: String) {
		var stockNo: String? = null
		val currentDay = LocalDate.now().toString()
		try {
			val lines = list.split("],")
			val tempList: MutableList<StockHistory> = mutableListOf()
			for (line in lines) {
				val dataOfOneDate = line.replace("[[", "").replace("[", "")
				val fields = dataOfOneDate.split(",").deleteQuotation()
				val history = fields.toStockHistory()
				tempList.add(history)
				if (tempList.size == 80) {
					stockHistoryPersistenceService.saveAll(tempList)
					tempList.clear()
				}
				stockNo = history.stockNo
			}
			stockHistoryPersistenceService.saveAll(tempList)
			logger.info("#history download stock = $stockNo, already download!")
			stockNo?.let {
				val stockInfo = stockInfoPersistenceService.findByStockNo(it.toNakedCode()).apply {
					this.downloadTimes = valuePlusOne(this.downloadTimes)
					this.haveDataTimes = valuePlusOne(this.haveDataTimes)
					this.lastDownloadAt = currentDay
					this.isDownload = true
				}
				stockInfoPersistenceService.save(stockInfo)
			}
		} catch (e: Exception) {
			stockNo?.let {
				val stockInfo = stockInfoPersistenceService.findByStockNo(it.toNakedCode()).apply {
					this.downloadTimes = valuePlusOne(this.downloadTimes)
				}
				stockInfoPersistenceService.save(stockInfo)
			}
		}
	}
	
	@PostMapping("/profit")
	@Async("asyncExecutor")
	fun quarterProfit(@RequestBody rawData: String) {
		try {
//			logger.info("${rawData.toJson()}")
			val rawList = rawData.split(",")
			val profit = if (rawList.size > 2) {
				rawList.toProfitQuarter()
			} else {
				logger.info("#quarterProfit skip with empty.")
				return
			}
//			logger.info("${profit.toJson()}")
			profitService.save(profit)
			logger.info("#quarterProfit ${profit.code} have download ${profit.statDate}")
		} catch (e: Exception) {
			logger.error("ReceiveController#quarterProfit error:", e)
		}
	}
	
	private fun valuePlusOne(value: String?): String {
		return BigDecimal.ONE.add(value?.toBigDecimalOrNull() ?: BigDecimal.ZERO).toString()
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(ReceiveController::class.java)
	}
}