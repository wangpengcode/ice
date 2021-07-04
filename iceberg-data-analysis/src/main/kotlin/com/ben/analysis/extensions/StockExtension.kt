package com.ben.analysis.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun String.toNakedCode(): String {
    return this.replace("sh.", "").replace("sz.", "").trim()
}

fun isBlowShadow(low: BigDecimal, close: BigDecimal, open: BigDecimal): Boolean {
    val theStandardPrice = if (close > open) open else close
    val diff = low.subtract(theStandardPrice).abs()
    val change = diff.divide(low, 20, RoundingMode.HALF_EVEN).multiply(BigDecimal("100"))
    return change > BigDecimal.ONE
}

fun String.toDateStr(): String {
    return this.substring(0, 10).replace("-", "")
}

fun String.isBefore(localDate: String): Boolean {
    val standard = localDate.toDateStr().toInt()
    val current = this.toDateStr().toInt()
    return current < standard
}