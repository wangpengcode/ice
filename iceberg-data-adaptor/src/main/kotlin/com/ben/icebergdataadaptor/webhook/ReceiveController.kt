package com.ben.icebergdataadaptor.webhook

import com.ben.icebergdataadaptor.extensions.*
import com.ben.icebergdataadaptor.persistence.entity.StockHistory
import com.ben.icebergdataadaptor.persistence.entity.StockInfo
import com.ben.icebergdataadaptor.persistence.service.StockHistoryPersistenceService
import com.ben.icebergdataadaptor.persistence.service.StockInfoPersistenceService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/file/upload")
class ReceiveController(
	val stockInfoPersistenceService: StockInfoPersistenceService,
	val stockHistoryPersistenceService: StockHistoryPersistenceService
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
				val industry = Industry(
						date = detail[0].replace("[", "").replace("[", "").replace("\"", "").trim(),
						code = detail[1].replace("*", "").replace("ST", "").replace("\"", "").trim(),
						codeName = decode(detail[2].replace("\"", "")),
						industry = decode(detail[3].replace("\"", "")),
						industryClassify = decode(detail[4].replace("\"", ""))
				).apply {
					this.nakedCode = code.toNakedCode()
				}
				industryList.add(industry)
			} catch (e: Exception) {
				continue
			}
		}
		stockInfoPersistenceService.queryAll()?.let {
			for (stock in it) {
				for (i in industryList) {
					if (i.nakedCode == stock.stockNo) {
						stock.apply {
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

		logger.info("industrys = $industryList")
	}
	
	@PostMapping("/all")
	fun uploadAllStock(@RequestBody list: String) {
		val a = list.split("],")
		val c = mutableListOf<StockInfo>()
		for (it in a) {
			try {
				val currentItem = it.split(",")
				val info = StockInfo(
					stockNo = currentItem[0].replace("[", "").replace("[", "").replace("[", "").replace("\"", "")
						.toNakedCode(),
					exchangeHouse = currentItem[0].split(".")[0].replace("[[\\", "").replace("\"", "").trim(),
					codeName = decode(
						currentItem[1].replace("\"", "").replace("*", "").replace("ST", "").replace("\"", "")
							.replace("\"", "").trim()
					),
					ipoDate = currentItem[2],
					outDate = currentItem[3],
					type = currentItem[4],
					ipoStatus = currentItem[5]
				)
				logger.info("uploadAllStock ${info}")
				c.add(info)
				if (c.size == 10) {
					stockInfoPersistenceService.saveAll(c)
					c.clear()
				}
			} catch (e: Exception) {
				logger.error("error ", e)
			}
		}
		if (list.isNotEmpty())
			stockInfoPersistenceService.saveAll(c)
	}
	
	@PostMapping("/history")
	fun history(@RequestBody list: String) {
		var stockNo: String? = null
		try {
			val a = list.split("],")
			// TODO need refactor.
			
			val list: MutableList<StockHistory> = mutableListOf()
			for (it in a) {
				val b = it.replace("[[", "").replace("[", "")
				val c = b.split(",").deleteQuotation()
				val history = StockHistory(
//				id = c[0] + c[1],
					date = c[0],
					code = c[1].replace("sh.", "").replace("sz.", "").replace("\"", "").replace(" ", "").toBigInteger(),
					stockNo = c[1],
					open = c[2],
					high = c[3],
					low = c[4],
					close = c[5],
					preclose = c[6],
					volume = c[7],
					amount = c[8],
					adjustflag = c[9],
					turn = c[10],
					tradestatus = c[11],
					pctChg = c[12],
					peTTM = c[13],
					pbMRQ = c[14],
					psTTM = c[15],
					pcfNcfTTM = c[16],
					isST = c[17].replace("]]", "")
				)
				list.add(history)
				if (list.size == 40) {
					stockHistoryPersistenceService.saveAll(list)
					list.clear()
				}
				stockNo = history.stockNo
			}
			stockHistoryPersistenceService.saveAll(list)
			logger.info("#history download stock = $stockNo, already download!")
			stockNo?.let {
				val stockInfo = stockInfoPersistenceService.findByStockNo(it.toNakedCode()).apply {
					this.downloadTimes =
						BigDecimal.ONE.add(this.downloadTimes?.toBigDecimalOrNull() ?: BigDecimal.ZERO).toString()
					this.haveDataTimes =
						BigDecimal.ONE.add(this.downloadTimes?.toBigDecimalOrNull() ?: BigDecimal.ZERO).toString()
				}
				stockInfoPersistenceService.save(stockInfo)
			}
		} catch (e: Exception) {
			logger.error("history error", e)
			stockNo?.let {
				val stockInfo = stockInfoPersistenceService.findByStockNo(it.toNakedCode()).apply {
					this.downloadTimes =
						BigDecimal.ONE.add(this.downloadTimes?.toBigDecimalOrNull() ?: BigDecimal.ZERO).toString()
				}
				stockInfoPersistenceService.save(stockInfo)
			}
		}
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(ReceiveController::class.java)
	}
}