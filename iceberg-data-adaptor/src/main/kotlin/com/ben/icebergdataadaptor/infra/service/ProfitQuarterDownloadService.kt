package com.ben.icebergdataadaptor.infra.service

import com.ben.icebergdataadaptor.constant.Constants
import com.ben.icebergdataadaptor.extensions.executeAsRuntimeCmd
import com.ben.icebergdataadaptor.webhook.WebHookUrl
import org.springframework.stereotype.Service

@Service
class ProfitQuarterDownloadService() {
	
	enum class Quarter(val code: String) {
		ONE("1"),
		TWO("2"),
		THREE("3"),
		FOUR("4");
	}
	
	fun profitQuarter(stock: String, year: String, quarter: Quarter) {
		try {
			// TODO need enhance the file path.
			val cmd =
				"python3 ${Constants.SYSTEM_PREFIX}${Constants.PROJECT_PREFIX}${Constants.QUARTER_PROFIT} ${WebHookUrl.BAO_STOCK_QUARTER_PROFIT} $stock $year ${quarter.code}"
			cmd.executeAsRuntimeCmd()
		} catch (e: Exception) {
			AllStockDownloadService.logger.info("#downAllStock error", e)
			throw e
		}
	}
}