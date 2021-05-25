package com.ben.icebergdataadaptor.extensions

import com.ben.icebergdataadaptor.exception.FormatException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.readValue

// JSON
val objectMapper: ObjectMapper = ignoreNullObjectMapper()

val snakeCaseObjectMapper = snakeCaseObjectMapper()

fun Any.toJson(): String = try {
    objectMapper.writeValueAsString(this)
} catch (e: Exception) {
    throw FormatException(e)
}

fun String.toJson() = this

inline fun <reified T> String.fromJson() = try {
    objectMapper.readValue<T>(this)
} catch (e: Exception) {
    throw FormatException(e)
}

inline fun <reified T> Any.convert(): T = try {
    objectMapper.convertValue(this)
} catch (e: Exception) {
    throw FormatException(e)
}

inline fun <reified T> Any.convertWithSnakeCase(): T = try {
    snakeCaseObjectMapper.convertValue(this)
} catch (e: Exception) {
    throw FormatException(e)
}
