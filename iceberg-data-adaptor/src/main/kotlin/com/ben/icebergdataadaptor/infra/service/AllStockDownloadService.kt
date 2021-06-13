package com.ben.icebergdataadaptor.infra.service

import com.ben.icebergdataadaptor.constant.Constants
import com.ben.icebergdataadaptor.extensions.executeAsRuntimeCmd
import com.ben.icebergdataadaptor.webhook.WebHookUrl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AllStockDownloadService {
	
	fun downAllStock(day: String? = null) {
		try {
			// TODO need enhance the file path.
			var cmd =
				"python3 ${Constants.SYSTEM_PREFIX}${Constants.PROJECT_PREFIX}${Constants.STOCK_INFO} ${WebHookUrl.BAO_STOCK_ALL_STOCK_CODE}"
			day?.let { cmd = "$cmd $day" }
			cmd.executeAsRuntimeCmd()
		} catch (e: Exception) {
			logger.info("#downAllStock error", e)
			throw e
		}
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(AllStockDownloadService::class.java)
	}
}