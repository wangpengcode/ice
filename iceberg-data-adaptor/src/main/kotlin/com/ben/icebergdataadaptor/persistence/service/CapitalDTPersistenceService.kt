package com.ben.icebergdataadaptor.persistence.service

import com.ben.icebergdataadaptor.persistence.entity.CapitalDT
import com.ben.icebergdataadaptor.persistence.repository.CapitalDTRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CapitalDTPersistenceService(val repository: CapitalDTRepository) {
	fun queryLastMonth(times: Int): List<CapitalDT> {
		return try {
			repository.queryLastMonth(times)
		} catch (e: Exception) {
			logger.error("CapitalDTPersistenceService#queryLastMonth error", e)
			throw e
		}
	}
	
	fun queryLastTwoMonth(times: Int): List<CapitalDT> {
		return try {
			repository.queryLastTwoMonth(times)
		} catch (e: Exception) {
			logger.error("CapitalDTPersistenceService#queryLastTwoMonth error", e)
			throw e
		}
	}
	
	fun queryLasThreeMonth(times: Int): List<CapitalDT> {
		return try {
			repository.queryLasThreeMonth(times)
		} catch (e: Exception) {
			logger.error("CapitalDTPersistenceService#queryLasThreeMonth error", e)
			throw e
		}
	}
	
	fun queryLastHalfYear(times: Int): List<CapitalDT> {
		return try {
			repository.queryLastHalfYear(times)
		} catch (e: Exception) {
			logger.error("CapitalDTPersistenceService#queryLastHalfYear error", e)
			throw e
		}
	}
	
	fun save(entity: CapitalDT) : CapitalDT? {
		return try {
			repository.save(entity)
		} catch (e: Exception) {
			logger.error("CapitalDTPersistenceService#save error", e)
			return null
		}
	}
	
	fun saveAll(list: List<CapitalDT>) : List<CapitalDT>? {
		return try {
			repository.saveAll(list).toList()
		} catch (e: Exception) {
			list.forEach { save(it) }
			return null
		}
	}
	
	companion object {
		val logger: Logger = LoggerFactory.getLogger(CapitalDTPersistenceService::class.java)
	}
}