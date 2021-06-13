package com.ben.icebergdataadaptor.infra.service

import com.ben.icebergdataadaptor.constant.Constants
import com.ben.icebergdataadaptor.extensions.executeAsRuntimeCmd
import com.ben.icebergdataadaptor.webhook.WebHookUrl
import org.springframework.stereotype.Service

@Service
class StockIndustryService {

    fun industry() {
        val cmd = "python3 ${Constants.SYSTEM_PREFIX}${Constants.PROJECT_PREFIX}${Constants.INDUSTRY} ${WebHookUrl.BAO_STOCK_INDUSTRY}"
        try {
            cmd.executeAsRuntimeCmd()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}