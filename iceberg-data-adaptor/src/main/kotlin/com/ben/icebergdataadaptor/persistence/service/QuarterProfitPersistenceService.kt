package com.ben.icebergdataadaptor.persistence.service

import com.ben.icebergdataadaptor.persistence.entity.ProfitQuarter
import com.ben.icebergdataadaptor.persistence.repository.ProfitRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class QuarterProfitPersistenceService(val profitRepository: ProfitRepository) {
	fun queryAll(): List<ProfitQuarter> {
		return try {
			profitRepository.findAll().toList()
		} catch (e: Exception) {
			logger.error("QuarterProfitService#saveAll error", e)
			throw e
		}
	}
	
	
	fun saveAll(profits: List<ProfitQuarter>): List<ProfitQuarter> {
		return try {
			profitRepository.saveAll(profits).toList()
		} catch (e: Exception) {
			logger.error("QuarterProfitService#saveAll error", e)
			throw e
		}
	}
	
	fun save(profit: ProfitQuarter): ProfitQuarter {
		return try {
			profitRepository.save(profit)
		} catch (e: Exception) {
			logger.error("QuarterProfitService#save error", e)
			throw e
		}
	}
	
	fun queryByStock(stockNo: String): List<ProfitQuarter> {
		return try {
			profitRepository.queryByStockNo(stockNo)
		} catch (e: Exception) {
			logger.error("QuarterProfitService#queryByStock error", e)
			throw e
		}
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(QuarterProfitPersistenceService::class.java)
	}
}