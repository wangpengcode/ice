package com.ben.icebergdataadaptor.webhook

object WebHookUrl {
	private const val CURRENT_SERVICE_URL = "http://localhost:19889"
	const val BAO_STOCK_ALL_STOCK_CODE = "$CURRENT_SERVICE_URL/file/upload/all"
	const val BAO_STOCK_INDUSTRY = "$CURRENT_SERVICE_URL/file/upload/industry"
	const val BAO_STOCK_STOCK_HISTORY = "$CURRENT_SERVICE_URL/file/upload/history"
	const val BAO_STOCK_QUARTER_PROFIT = "$CURRENT_SERVICE_URL/file/upload/profit"
}