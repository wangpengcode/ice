package com.ben.icebergdataadaptor.controller

import com.ben.icebergdataadaptor.api.BaoStockApi
import com.ben.icebergdataadaptor.job.IcebergJob
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate


@RestController
@RequestMapping("/bao")
class BaoStockController(val baoStockApi: BaoStockApi,val icebergJob: IcebergJob) {
	
	@GetMapping("/python")
	fun test(): String {
		baoStockApi.executeDownloadAllStockByDay(LocalDate.now().plusDays(-2).toString())
		return "hello"
	}

	@GetMapping("/industry")
	fun industry() {
		baoStockApi.industry()
	}

	@GetMapping("/history")
	fun downloadAndPersistenceStockHistory() {
		icebergJob.downloadAndPersistenceStockHistory()
	}
}