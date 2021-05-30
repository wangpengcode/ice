package com.ben.analysis.transformer

import com.ben.analysis.persistence.entity.StockHistory
import com.ben.analysis.persistence.entity.SuperStar

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