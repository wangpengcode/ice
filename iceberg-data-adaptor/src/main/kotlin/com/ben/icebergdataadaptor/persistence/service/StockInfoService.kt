package com.ben.icebergdataadaptor.persistence.service

import com.ben.icebergdataadaptor.persistence.entity.StockInfo
import com.ben.icebergdataadaptor.persistence.repository.StockInfoRepository
import org.springframework.stereotype.Service

@Service
class StockInfoService (
        val stockInfoRepository: StockInfoRepository
        ){
    fun save(entity: StockInfo) {
        stockInfoRepository.save(entity)
    }

    fun findById(id: String) {
        stockInfoRepository.findById(id)
    }
}