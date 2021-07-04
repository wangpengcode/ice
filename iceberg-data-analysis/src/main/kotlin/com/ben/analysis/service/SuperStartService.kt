package com.ben.analysis.service

import com.ben.analysis.constants.Constants
import com.ben.analysis.extensions.toNakedCode
import com.ben.analysis.persistence.entity.SuperStar
import com.ben.analysis.persistence.repository.StockHistoryRepository
import com.ben.analysis.persistence.repository.StockInfoRepository
import com.ben.analysis.persistence.repository.SuperStarRepository
import com.ben.analysis.transformer.toSuperStar
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.streams.toList

@Service
class SuperStartService(
        val superStarRepository: SuperStarRepository,
        val stockInfoRepository: StockInfoRepository,
        val stockHistoryRepository: StockHistoryRepository
) {

    @Async(value = "asyncExecutor")
    fun superStar(stockNo: String) {
        try {
            val histories = stockHistoryRepository.queryHistoryByStockNo(stockNo,stockNo.toNakedCode().toBigInteger())
            if (histories == null || histories.isEmpty()) return
            val stockNo = histories[0].stockNo
            val lastSuperDate = superStarRepository.queryTheNewestDay(stockNo)
            val stockInfo = stockInfoRepository.findByCodeWithEx(stockNo)
            val needFindList = lastSuperDate?.let {
                histories.stream().filter { it.date > lastSuperDate }?.toList()
            } ?: histories
            var superStar: SuperStar
            val superStars = mutableListOf<SuperStar>()
            if (needFindList != null && needFindList.isNotEmpty())
                for (history in needFindList) {
                    try {
                        if (history.pctChg?.toBigDecimalOrNull() ?: BigDecimal.ZERO >= Constants.ZTB_CHANGE) {
                            superStar = history.toSuperStar(codeName = stockInfo?.codeName.orEmpty(),
                                    industry = stockInfo?.industry.orEmpty(),
                                    dt = false,
                                    zt = true)
                            superStars.add(superStar)
                        }
                        if (history.pctChg?.toBigDecimalOrNull() ?: BigDecimal.ZERO <= Constants.DTB_CHANGE) {
                            superStar = history.toSuperStar(codeName = stockInfo?.codeName.orEmpty(),
                                    industry = stockInfo?.industry.orEmpty(),
                                    dt = true,
                                    zt = false)
                            superStars.add(superStar)
                        }
                    } catch (e: Exception) {
                        logger.error("#superStar inner error, $history")
                        continue
                    }
                }
            superStars.chunked(40).forEach {
                superStarRepository.saveAll(it)
            }
            logger.info("#superStar already do super star of $stockNo")
        } catch (e: Exception) {
            logger.error("#superStar error $stockNo", e)
        }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SuperStartService::class.java)
    }
}