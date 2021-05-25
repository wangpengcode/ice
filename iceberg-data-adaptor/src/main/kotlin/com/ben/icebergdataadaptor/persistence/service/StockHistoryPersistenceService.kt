package com.ben.icebergdataadaptor.persistence.service

import com.ben.icebergdataadaptor.persistence.entity.StockHistory
import com.ben.icebergdataadaptor.persistence.repository.StockHistoryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StockHistoryPersistenceService(val stockHistoryRepository: StockHistoryRepository) {
	fun save(entity: StockHistory) = try {
		stockHistoryRepository.save(entity)
	} catch (e: Exception) {
		logger.error("#save with error", e)
		throw e
	}
	
	fun queryTheOldestDay(stockNo: String) = try {
		stockHistoryRepository.queryTheOldestDay(stockNo)
	} catch (e: Exception) {
		logger.error("#queryTheOldestDay error", e)
		null
	}
	
	fun queryTheNewestDay(stockNo: String) = try {
		stockHistoryRepository.queryTheNewestDay(stockNo)
	} catch (e: Exception) {
		logger.error("#queryTheNewestDay error", e)
		null
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(StockHistoryPersistenceService::class.java)
	}
}