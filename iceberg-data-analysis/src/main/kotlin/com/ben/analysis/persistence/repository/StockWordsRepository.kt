package com.ben.analysis.persistence.repository

import com.ben.analysis.persistence.entity.StockWords
import org.springframework.data.repository.CrudRepository

interface StockWordsRepository: CrudRepository<StockWords,String> {

}