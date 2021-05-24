package com.ben.icebergdataadaptor.webhook

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
}