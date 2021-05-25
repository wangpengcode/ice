package com.ben.icebergdataadaptor.extensions

import java.math.BigDecimal
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

fun <T> HashSet<T?>.isOnlyOneT(): Boolean {
    return this.size <= 1
}

fun <T> HashSet<T?>.putT(t: T?) {
    t?.let { this.add(t) }
}

fun <T> getNullableSet() = HashSet<T>()

fun Any?.hasValue(): Boolean {
    return this != null
}

fun String.deleteQuotation(): String {
    return this.replace("\"","")
}

fun List<String>.deleteQuotation(): List<String> {
    return this.map { it -> it.deleteQuotation().replace(" ","") }.toList()
}

fun <T> Any?.value(): T {
    return this as T
}

fun String?.isEmpty(): Boolean {
    return this == null || "" == this
}

fun String?.isNotEmpty(): Boolean {
    return this != null && "" != this
}

fun String?.toBigDecimalDefaultZero(): BigDecimal {
    return if (this == null || this!!.isBlank())
        BigDecimal.ZERO
    else
        this.toBigDecimal()
}

inline fun <reified T : Any> distinctValueOrError(list: List<T>?, fieldName: KProperty1<T, String>): String? {
    if (list == null) throw Exception("list can not be null.")
    val values = hashSetOf<String>()
    for (t in list) {
        val clazz = t::class
        clazz.memberProperties.forEach {
            if (fieldName == it)
                values.add(it.getter.call(t).toString())
        }
    }
    return when {
        values.size > 1 -> throw Exception("$fieldName have too many value.")
        values.size == 0 -> null
        else -> values.first()
    }
}