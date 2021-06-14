package com.ben.analysis.service

import com.ben.analysis.persistence.entity.StockHistory
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
import java.math.BigInteger
import java.text.DecimalFormat

@Service
class StockWordsService(
	val stockInfoRepository: StockInfoRepository,
	val stockHistoryRepository: StockHistoryRepository,
	val stockWordsRepository: StockWordsRepository
) {
	val logger: Logger = LoggerFactory.getLogger(StockWordsService::class.java)
	
	@Async(value = "asyncExecutor")
	fun stockWords() {
		val stocks = stockInfoRepository.findStocks()
		val stockWordsList = mutableListOf<StockWords>()
		
		if (stocks != null && stocks.isNotEmpty())
			for (stock in stocks) {
				try {
					val stockNo = stock.codeWithEx
					val newestHistories = stockNo?.let { it ->
						stockHistoryRepository.queryHistoryOrderByDateDesc(it).sortedByDescending { it.date }
					} ?: continue
					if (newestHistories.size < 5) continue
					val last5 = newestHistories.subList(0, 5)
					val minHistory = last5.minWithOrNull(compareBy { it.low?.toBigDecimal() }) ?: continue
					val stockInfo = stockInfoRepository.findByCodeWithEx(stockNo)
					minHistory.let { it ->
						if (it.low !== null && isValidFormat(it.low.toBigDecimal())) {
							val words = it.toStockWords(
								codeName = stockInfo?.codeName.orEmpty(),
								industry = stockInfo?.industry.orEmpty()
							).apply {
								this.last_day =
									last5.filter { it2 -> it2.date > minHistory.date }.toList().size.toBigInteger()
										?: BigInteger.ZERO
							}
//							stockWordsList.add(words)
							stockWordsRepository.save(words)
							logger.info("#stockWords already done stock words ${stockNo},${it.low},${it.date}")
						}
					}
//					if (stockWordsList.size == 40) {
//						stockWordsRepository.saveAll(stockWordsList)
//						stockWordsList.clear()
//					}
				} catch (e: Exception) {
					logger.error("#stockWords error stock = $stock", e)
					continue
				}
			}
		if (stockWordsList.isNotEmpty())
			stockWordsRepository.saveAll(stockWordsList)
	}
	
	@Async(value = "asyncExecutor")
	fun checkWordsValid(lastWords: StockWords) {
		try {
			val newestHistory = stockHistoryRepository.queryHistoryOrderByDateDesc(lastWords.code_with_ex)
				.sortedByDescending { it.date }.first()
			if (newestHistory.date > lastWords.date && isValidStockWords(lastWords, newestHistory)) {
				lastWords.apply {
					last_day = this.last_day?.let { it.add(BigInteger.ONE) } ?: BigInteger.ONE
				}
				stockWordsRepository.save(lastWords)
			}
			
			if (newestHistory.date > lastWords.date && ! isValidStockWords(lastWords, newestHistory)) {
				lastWords.apply {
					words_is_valid = false
				}
				stockWordsRepository.save(lastWords)
			}
			
		} catch (e: Exception) {
			logger.info("#checkWordsValid error", e)
		}
	}
	
	fun isValidStockWords(lastWords: StockWords, currentHistory: StockHistory): Boolean {
		return (lastWords.lowest?.toDouble() ?: 0.0 <= currentHistory.low?.toDouble() ?: 0.0)
	}
	
//	fun isValidPosition(maybeWords: StockHistory, list: List<StockHistory>): Boolean {
//
//	}
	
	fun isValidFormat(amount: BigDecimal): Boolean {
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
}