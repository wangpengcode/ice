package com.ben.analysis.transformer

import com.ben.analysis.persistence.entity.StockHistory
import com.ben.analysis.persistence.entity.StockWords
import com.ben.analysis.persistence.entity.SuperStar
import java.text.DecimalFormat

fun StockHistory.toSuperStar(codeName: String, industry: String, dt: Boolean = false, zt: Boolean = false) = SuperStar(
        id = null,
        code_with_ex = stockNo,
        date = date,
        year = date.substring(0, 4),
        month = date.substring(5, 7),
        code_name = codeName,
        industry = industry,
        change = pctChg,
        dt = dt,
        zt = zt
)

fun StockHistory.toStockWords(codeName: String, industry: String, wordsIsValid: Boolean = false) = StockWords(
        id = null,
        code_with_ex = stockNo,
        code_name = codeName,
        industry = industry,
        date = date,
        lowest = low?.let { DecimalFormat("#.00").format(it.toBigDecimal()).toString() },
        words_is_valid = wordsIsValid,
        is_st = isST == "1"
)