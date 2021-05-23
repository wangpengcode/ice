package com.ben.icebergdataadaptor.service

import com.ben.icebergdataadaptor.api.BaoStockApi
import com.ben.icebergdataadaptor.infra.service.AllStockDownloadService
import org.springframework.stereotype.Service

@Service
class BaoStockImpl(
	val allStockDownloadService: AllStockDownloadService
) : BaoStockApi {
	
	override fun executeDownloadAllStockByDay(csvName: String, day: String?) {
		System.out.println("BaoStockImpl#executeDownloadAllStockByDay ")
		allStockDownloadService.downAllStock(csvName, day)
	}
}