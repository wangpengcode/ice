package com.ben.icebergdataadaptor.infra.service

import com.ben.icebergdataadaptor.extensions.executeAsRuntimeCmd
import com.ben.icebergdataadaptor.webhook.WebHookUrl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StockHistoryDownloadService {
	
	fun stockHistory(stockCode: String, startDay: String, endDay: String) {
		try {
			// TODO need enhance the file path.
			var cmd =
				"python3 /Users/wangpeng/Documents/my-hobbit/hobbit-code/ice/iceberg-data-adaptor/src/main/kotlin/com/ben/icebergdataadaptor/infra/py/StockHistory.py ${WebHookUrl.BAO_STOCK_STOCK_HISTORY}"
			cmd = "$cmd $stockCode $startDay $endDay"
			cmd.executeAsRuntimeCmd()
		} catch (e: Exception) {
			logger.info("#stockHistory error", e)
			throw e
		}
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(StockHistoryDownloadService::class.java)
	}
}