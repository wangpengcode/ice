package com.ben.icebergdataadaptor.persistence.service

import com.ben.icebergdataadaptor.persistence.entity.StockInfo
import com.ben.icebergdataadaptor.persistence.repository.StockInfoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StockInfoPersistenceService(
    val stockInfoRepository: StockInfoRepository
) {
	fun save(entity: StockInfo) = try {
		stockInfoRepository.save(entity)
	} catch (e: Exception) {
		logger.error("#save error:", e)
	}
	
	fun findById(id: String) {
		stockInfoRepository.findById(id)
	}
    
    companion object {
        val logger: Logger = LoggerFactory.getLogger(StockInfoPersistenceService::class.java)
    }
}