package com.ben.icebergdataadaptor.webhook

import com.ben.icebergdataadaptor.persistence.entity.StockHistory
import com.ben.icebergdataadaptor.persistence.entity.StockInfo
import com.ben.icebergdataadaptor.persistence.service.StockInfoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/file/upload")
class UploadFileController(val stockInfoService: StockInfoService) {
	@PostMapping("/all")
	fun uploadAllStock(@RequestBody list: String) {
		val a = list.replace("[","").replace("]","").split(',')
		println(a)
		for(i in a) {
			stockInfoService.save(StockInfo(exchangeHouse = i.split(".")[0],  stockNo= i.split(".")[1]))
		}
	}
	
	@PostMapping("/history")
	fun history(@RequestBody list: String) {
		println(list)
		val a = list.split("],")
		a.forEach {
			val b = it.replace("[[", "").replace("[", "")
			val c = b.split(",")
			val history = StockHistory(
				id = c[0]. + c[1],
				date = c[0],
				code = c[1].replace("sh.","").replace("sz.","").replace("\"","").replace(" ","").toBigInteger(),
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
			println(history)
		}
	}
	
}