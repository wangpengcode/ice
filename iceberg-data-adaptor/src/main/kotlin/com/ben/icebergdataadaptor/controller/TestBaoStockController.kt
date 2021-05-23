package com.ben.icebergdataadaptor.controller

import com.ben.icebergdataadaptor.api.BaoStockApi
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/bao")
class TestBaoStockController(val baoStockApi: BaoStockApi) {
	
	@GetMapping("/python")
	fun test() : String {
		System.out.println("python")
		baoStockApi.executeDownloadAllStockByDay("abc.csv", "2021-05-21")
		System.out.println("python end")
		return "hello"
	}
}