package com.ben.icebergdataadaptor.persistence.repository

import com.ben.icebergdataadaptor.persistence.entity.CapitalDT
import com.ben.icebergdataadaptor.persistence.entity.ProfitQuarter
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CapitalDTRepository: CrudRepository<CapitalDT, String> {
	@Query("FROM CapitalDT where code = ?1")
	fun queryByStockNo(stock: String): List<CapitalDT>
	
	@Query("FROM CapitalDT where lastMonth >= ?1")
	fun queryLastMonth(times: Int): List<CapitalDT>
	
	@Query("FROM CapitalDT where lastTwoMonth >= ?1")
	fun queryLastTwoMonth(times: Int): List<CapitalDT>
	
	@Query("FROM CapitalDT where lasThreeMonth >= ?1")
	fun queryLasThreeMonth(times: Int): List<CapitalDT>
	
	@Query("FROM CapitalDT where lastHalfYear >= ?1")
	fun queryLastHalfYear(times: Int): List<CapitalDT>
}