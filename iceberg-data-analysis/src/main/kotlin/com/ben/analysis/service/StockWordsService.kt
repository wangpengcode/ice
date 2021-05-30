package com.ben.analysis.service

import com.ben.analysis.persistence.entity.StockWords
import com.ben.analysis.persistence.repository.StockHistoryRepository
import com.ben.analysis.persistence.repository.StockInfoRepository
import com.ben.analysis.persistence.repository.StockWordsRepository
import com.ben.analysis.transformer.toStockWords
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.text.DecimalFormat

@Service
class StockWordsService(
        val stockInfoRepository: StockInfoRepository,
        val stockHistoryRepository: StockHistoryRepository,
        val stockWordsRepository: StockWordsRepository
) {
    @Async(value = "asyncExecutor")
    fun stockWords() {
        val stocks = stockInfoRepository.findStocks()
        val stockWordsList = mutableListOf<StockWords>()

        if (stocks != null && stocks.isNotEmpty())
            for (stock in stocks) {
                try {
                    val stockNo = stock.codeWithEx
                    val newestHistory = stockNo?.let { it ->
                        stockHistoryRepository.queryTheNewestHistory(it).sortedByDescending { it.date }.first()
                    } ?: continue
                    val stockInfo = stockInfoRepository.findByCodeWithEx(stockNo)
                    newestHistory.let {
                        if (it.low !== null && isWords(it.low.toBigDecimal())) {
                            val words = it.toStockWords(
                                    codeName = stockInfo?.codeName.orEmpty(),
                                    industry = stockInfo?.industry.orEmpty()
                            )
                            stockWordsList.add(words)
                            logger.info("#stockWords already done stock words ${stockNo},${it.low}")
                        }
                    }
                    if (stockWordsList.size == 40) {
                        stockWordsRepository.saveAll(stockWordsList)
                        stockWordsList.clear()
                    }
                } catch (e: Exception) {
                    logger.error("#stockWords error stock = $stock", e)
                }
            }
        if (stockWordsList.isNotEmpty())
            stockWordsRepository.saveAll(stockWordsList)
    }

    fun isWords(amount: BigDecimal): Boolean {
        val format = DecimalFormat("#.00").format(amount)
        val intValue = format.split(".")[0]
        val dotValue = format.split(".")[1]
        val char1 = dotValue.take(1)
        val char2 = dotValue.takeLast(1)
        return if (intValue == dotValue) {
            true
        } else {
            char1 == char2
        }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(StockWordsService::class.java)
    }
}