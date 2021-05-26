package com.ben.icebergdataadaptor.service

import com.ben.icebergdataadaptor.api.BaoStockApi
import com.ben.icebergdataadaptor.infra.service.AllStockDownloadService
import com.ben.icebergdataadaptor.infra.service.StockIndustryService
import org.springframework.stereotype.Service

@Service
class BaoStockImpl(
	val allStockDownloadService: AllStockDownloadService,
	val stockIndustryService: StockIndustryService
) : BaoStockApi {
	
	override fun executeDownloadAllStockByDay(day: String?) {
		System.out.println("BaoStockImpl#executeDownloadAllStockByDay ")
		allStockDownloadService.downAllStock(day)
	}

	override fun industry() {
		stockIndustryService.industry()
	}
}