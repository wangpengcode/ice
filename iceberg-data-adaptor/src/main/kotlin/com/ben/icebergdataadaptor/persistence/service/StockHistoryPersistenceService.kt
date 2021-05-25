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
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(StockHistoryPersistenceService::class.java)
	}
}