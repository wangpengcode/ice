package com.ben.analysis.persistence.repository

import com.ben.analysis.persistence.entity.StockInfo
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StockInfoRepository : CrudRepository<StockInfo, String> {
	@Query("FROM StockInfo a where a.stockNo= ?1")
	fun findByStockNo(stockNo: String): StockInfo
}