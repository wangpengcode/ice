package com.ben.icebergdataadaptor.persistence.repository

import com.ben.icebergdataadaptor.persistence.entity.StockInfo
import org.springframework.data.repository.CrudRepository

interface StockInfoRepository : CrudRepository<StockInfo, String> {
}