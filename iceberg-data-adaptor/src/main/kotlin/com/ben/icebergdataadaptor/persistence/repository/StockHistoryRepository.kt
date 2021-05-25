package com.ben.icebergdataadaptor.persistence.repository

import com.ben.icebergdataadaptor.persistence.entity.StockHistory
import org.springframework.data.repository.CrudRepository

interface StockHistoryRepository : CrudRepository<StockHistory, String> {
}