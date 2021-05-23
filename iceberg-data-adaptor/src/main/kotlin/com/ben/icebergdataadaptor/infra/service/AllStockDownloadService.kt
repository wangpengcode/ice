package com.ben.icebergdataadaptor.infra.service

import org.springframework.stereotype.Service
import java.io.IOException

@Service
class AllStockDownloadService {
	
	fun downAllStock(csvName: String, day: String? = null) {
		var process: Process? = null
		System.out.println("AllStockDownloadService.downAllStock")
		try {
			val exe = "python3"
			val pythonFile = "../py/AllStockList.py"
			val cmdArr = if (day == null) {
				arrayOf(exe, pythonFile, csvName)
			} else {
				arrayOf(exe, pythonFile, csvName, day)
			}
			process = Runtime.getRuntime().exec("python3 /Users/wangpeng/Documents/code/test-code/ice/iceberg-data-adaptor/src/main/kotlin/com/ben/icebergdataadaptor/infra/py/AllStockList.py")
			val result = process.waitFor()
			System.out.println("AllStockDownloadService.downAllStock $result")
		} catch (e: Exception) {
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
		const val WAIT_MILLS = 1 * 60 * 1000L
	}
}