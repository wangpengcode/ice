package com.ben.icebergdataadaptor.api

interface BaoStockApi {
	/**
	 * if day is none, we set the day -3 is what we need.
	 */
	fun executeDownloadAllStockByDay(csvName: String, day: String? = null)
}