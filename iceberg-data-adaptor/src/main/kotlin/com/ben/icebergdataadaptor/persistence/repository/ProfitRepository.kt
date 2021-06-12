package com.ben.icebergdataadaptor.persistence.repository

import com.ben.icebergdataadaptor.persistence.entity.ProfitQuarter
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ProfitRepository: CrudRepository<ProfitQuarter, String> {
	@Query("FROM ProfitQuarter where code = ?1")
	fun queryByStockNo(stock: String): List<ProfitQuarter>
}