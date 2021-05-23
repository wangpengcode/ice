package com.ben.icebergdataadaptor.controller

import com.ben.icebergdataadaptor.persistence.entity.StockInfo
import com.ben.icebergdataadaptor.persistence.service.StockInfoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/file/upload")
class UploadFileController(val stockInfoService: StockInfoService) {
	@PostMapping("/all")
	fun uploadAllStock(@RequestBody list: String) {
		val a = list.replace("[","").replace("]","").split(',')
		for(i in a) {
			stockInfoService.save(StockInfo(stockNo = i))
		}
	}
}