package com.ben.icebergdataadaptor.infra.service

import com.ben.icebergdataadaptor.extensions.executeAsRuntimeCmd
import com.ben.icebergdataadaptor.webhook.WebHookUrl
import org.springframework.stereotype.Service

@Service
class StockIndustryService {

    fun industry() {
        val cmd = "python3 /Users/wangpeng/Documents/code/test-code/ice/iceberg-data-adaptor/src/main/kotlin/com/ben/icebergdataadaptor/infra/py/StockIndustry.py ${WebHookUrl.BAO_STOCK_INDUSTRY}"
        try {
            cmd.executeAsRuntimeCmd()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}