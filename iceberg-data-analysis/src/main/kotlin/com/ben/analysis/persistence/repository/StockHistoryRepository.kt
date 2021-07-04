package com.ben.analysis.persistence.repository

import com.ben.analysis.persistence.entity.StockHistory
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface StockHistoryRepository : CrudRepository<StockHistory, String> {
	
	@Query("select min(a.date) from StockHistory a where a.stockNo = ?1")
	fun queryTheOldestDay(stockNo: String): String?
	
	@Query("select max(a.date) from StockHistory a where a.stockNo = ?1")
	fun queryTheNewestDay(stockNo: String): String?

	@Query("from StockHistory a where a.stockNo = ?1 and a.code =?2")
	fun queryHistoryByStockNo(stockNo: String, code: BigInteger): List<StockHistory>?

	@Query("from StockHistory a where a.stockNo = ?1 order by a.date desc")
	fun queryHistoryOrderByDateDesc(stockNo: String): List<StockHistory>
}