package com.ben.icebergdataadaptor.webhook

import com.ben.icebergdataadaptor.extensions.deleteQuotation
import com.ben.icebergdataadaptor.extensions.getNullableSet
import com.ben.icebergdataadaptor.persistence.entity.StockHistory
import com.ben.icebergdataadaptor.persistence.entity.StockInfo
import com.ben.icebergdataadaptor.persistence.service.StockHistoryPersistenceService
import com.ben.icebergdataadaptor.persistence.service.StockInfoPersistenceService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/file/upload")
class ReceiveController(
	val stockInfoPersistenceService: StockInfoPersistenceService,
	val stockHistoryPersistenceService: StockHistoryPersistenceService
	) {
	@PostMapping("/all")
	fun uploadAllStock(@RequestBody list: String) {
		val a = list.replace("[","").replace("]","").split(',').deleteQuotation()
		println(a)
		for(i in a) {
			stockInfoPersistenceService.save(StockInfo(exchangeHouse = i.split(".")[0],  stockNo= i.split(".")[1]))
		}
	}
	
	@PostMapping("/history")
	fun history(@RequestBody list: String) {
		val stockSet = getNullableSet<String>()
		val a = list.split("],")
		// TODO need refactor.
		var stockNo: String? = null
		a.forEach {
			val b = it.replace("[[", "").replace("[", "")
			val c = b.split(",").deleteQuotation()
			val history = StockHistory(
//				id = c[0] + c[1],
				date = c[0],
				code = c[1].replace("sh.","").replace("sz.","").replace("\"","").replace(" ","").toBigInteger(),
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
				isST = c[17]
			)
			stockNo = history.stockNo
			stockSet.add(history.stockNo)
			stockHistoryPersistenceService.save(history);
		}
		logger.info("#history download stock = $stockNo, already download ${stockSet.size}!")
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(ReceiveController::class.java)
	}
}