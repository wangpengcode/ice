package com.ben.icebergdataadaptor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class IcebergDataAdaptorApplication

fun main(args: Array<String>) {
    runApplication<IcebergDataAdaptorApplication>(*args)
}
