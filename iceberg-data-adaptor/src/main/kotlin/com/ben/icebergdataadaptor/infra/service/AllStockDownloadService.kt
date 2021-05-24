package com.ben.icebergdataadaptor.infra.service

import com.ben.icebergdataadaptor.webhook.WebHookUrl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AllStockDownloadService {
	
	fun downAllStock(day: String? = null) {
		var process: Process? = null
		try {
			// TODO need enhance the file path.
			var cmd =
				"python3 /Users/wangpeng/Documents/code/test-code/ice/iceberg-data-adaptor/src/main/kotlin/com/ben/icebergdataadaptor/infra/py/AllStockList.py ${WebHookUrl.BAO_STOCK_ALL_STOCK_CODE}"
			day?.let { cmd = "$cmd $day" }
			process = Runtime.getRuntime().exec(cmd)
			val result = process.waitFor()
			logger.info("#downAllStock $result")
		} catch (e: Exception) {
			logger.info("#downAllStock error", e)
			e.printStackTrace()
		} catch (e: InterruptedException) {
			e.printStackTrace()
		} finally {
			if (process != null) {
				process.errorStream.close()
				process.inputStream.close()
				process.outputStream.close()
			}
		}
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(AllStockDownloadService::class.java)
	}
}