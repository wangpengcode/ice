package com.ben.analysis.persistence.repository

import com.ben.analysis.persistence.entity.StockInfo
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StockInfoRepository : CrudRepository<StockInfo, String> {
	@Query("FROM StockInfo a where a.stockNo= ?1")
	fun findByStockNo(stockNo: String): StockInfo

	@Query("FROM StockInfo a where a.type= '1'")
	fun findStocks(): List<StockInfo>?

	@Query("FROM StockInfo a where a.codeWithEx= ?1")
	fun findByCodeWithEx(codeWithEx: String): StockInfo?
}