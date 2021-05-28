package com.ben.icebergdataadaptor.persistence.repository

import com.ben.icebergdataadaptor.persistence.entity.StockHistory
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StockHistoryRepository : CrudRepository<StockHistory, String> {

    @Query("select min(a.date) from StockHistory a where a.stockNo = ?1 and a.code =?2")
    fun queryTheOldestDay(stockNo: String,code: String): String?

    @Query("select max(a.date) from StockHistory a where a.stockNo = ?1 and a.code = ?2")
    fun queryTheNewestDay(stockNo: String, code: String): String?
}