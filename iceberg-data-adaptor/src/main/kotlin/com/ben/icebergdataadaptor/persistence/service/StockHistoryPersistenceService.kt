package com.ben.icebergdataadaptor.persistence.service

import com.ben.icebergdataadaptor.persistence.entity.StockHistory
import com.ben.icebergdataadaptor.persistence.repository.StockHistoryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class StockHistoryPersistenceService(val stockHistoryRepository: StockHistoryRepository) {
	fun saveAll(entities: List<StockHistory>): MutableIterable<StockHistory>? = try {
		if (entities.isNotEmpty()) {
			stockHistoryRepository.saveAll(entities)
		} else null
	} catch (e: Exception) {
		logger.error("#saveAll with error {}", e.message)
		entities.forEach { save(it) }
		null
	}
	
	fun save(entity: StockHistory) = try {
		stockHistoryRepository.save(entity)
	} catch (e: Exception) {
		logger.error("#save with error: {}", e.message)
		throw e
	}
	
	fun queryTheOldestDay(stockNo: String, code: BigInteger) = try {
		stockHistoryRepository.queryTheOldestDay(stockNo,code)
	} catch (e: Exception) {
		logger.error("#queryTheOldestDay error", e)
		null
	}
	
	fun queryTheNewestDay(stockNo: String,code: BigInteger) = try {
		stockHistoryRepository.queryTheNewestDay(stockNo,code)
	} catch (e: Exception) {
		logger.error("#queryTheNewestDay error", e)
		null
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(StockHistoryPersistenceService::class.java)
	}
}