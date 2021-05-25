package com.ben.icebergdataadaptor.infra.service

import com.ben.icebergdataadaptor.webhook.WebHookUrl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StockHistoryService {
	
	fun stockHistory(stockCode: String, startDay: String, endDay: String) {
		var process: Process? = null
		try {
			// TODO need enhance the file path.
			var cmd =
				"python3 /Users/wangpeng/Documents/code/test-code/ice/iceberg-data-adaptor/src/main/kotlin/com/ben/icebergdataadaptor/infra/py/StockHistory.py ${WebHookUrl.BAO_STOCK_STOCK_HISTORY}"
			cmd = "$cmd $stockCode $startDay $endDay"
			process = Runtime.getRuntime().exec(cmd)
			val result = process.waitFor()
			logger.info("#stockHistory $result")
		} catch (e: Exception) {
			logger.info("#stockHistory error", e)
			e.printStackTrace()
		} catch (e: InterruptedException) {
			e.printStackTrace()
		} finally { // TODO need refactor this duplicate code
			if (process != null) {
				process.errorStream.close()
				process.inputStream.close()
				process.outputStream.close()
			}
		}
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(StockHistoryService::class.java)
	}
}