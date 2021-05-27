package com.ben.analysis.persistence.service

import com.ben.analysis.persistence.entity.StockInfo
import com.ben.analysis.persistence.repository.StockInfoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StockInfoPersistenceService(
    val stockInfoRepository: StockInfoRepository
) {
	fun saveAll(entities: List<StockInfo>) = try {
		stockInfoRepository.saveAll(entities)
	} catch (e: Exception) {
		logger.error("saveAll error", e)
	}
	fun save(entity: StockInfo) = try {
		stockInfoRepository.save(entity)
	} catch (e: Exception) {
		logger.error("#save error:", e)
	}
	
	fun findByStockNo(stockNo: String): StockInfo {
		return stockInfoRepository.findByStockNo(stockNo)
	}
	
	fun queryAll(): List<StockInfo>? = try {
		stockInfoRepository.findAll().toList()
	} catch (e: Exception) {
		StockHistoryPersistenceService.logger.error("#queryAll error", e)
		null
	}
    
    companion object {
        val logger: Logger = LoggerFactory.getLogger(StockInfoPersistenceService::class.java)
    }
}