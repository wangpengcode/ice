package com.ben.icebergdataadaptor.controller

import com.ben.icebergdataadaptor.api.BaoStockApi
import com.ben.icebergdataadaptor.constant.Constants
import com.ben.icebergdataadaptor.extensions.toJson
import com.ben.icebergdataadaptor.job.IcebergJob
import com.ben.icebergdataadaptor.job.ProfitJob
import com.ben.icebergdataadaptor.persistence.entity.CapitalDT
import com.ben.icebergdataadaptor.persistence.service.CapitalDTPersistenceService
import com.ben.icebergdataadaptor.persistence.service.StockInfoPersistenceService
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.time.LocalDate
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.streams.toList


@RestController
@RequestMapping("/bao")
class BaoStockController(
        val baoStockApi: BaoStockApi,
        val icebergJob: IcebergJob,
        val profitJob: ProfitJob,
        val capitalDTPersistenceService: CapitalDTPersistenceService,
        val stockInfoPer: StockInfoPersistenceService
) {
    data class Code(
            val code: String,
            val codeName: String?
    )
    @GetMapping("/get/new/dt")
    fun newDT(): List<Code> {
        val maxDate = capitalDTPersistenceService.queryMaxDate()
        logger.info("BaoStockController#maxDate = $maxDate")
        var list: MutableList<Code> = mutableListOf()
        maxDate?.let { it ->
            val codes = capitalDTPersistenceService.queryByDate(it.trim())
            logger.info("BaoStockController#codes?.size = ${codes?.size}")
            if (codes != null) {
                logger.info("maxDate $it have size = ${codes.size}")
                var listSize: List<CapitalDT>?
                for (i in codes) {
                    listSize = capitalDTPersistenceService.queryByCode(i.code)
//                    logger.info("${i.code} have size = ${listSize?.size}")
                    if (listSize != null && listSize.size == 2) {
                        val code = Code(code = i.code, codeName = i.codeName)
                        list.add(code)
                    }
                }
            }
        }
        return list
    }

    @GetMapping("/python")
    fun test(): String {
        baoStockApi.executeDownloadAllStockByDay(LocalDate.now().plusDays(-2).toString())
        return "hello"
    }

    @GetMapping("/industry")
    fun industry() {
        baoStockApi.industry()
    }

    @GetMapping("/history")
    fun downloadAndPersistenceStockHistory() {
        icebergJob.downloadAndPersistenceStockHistory()
    }

    @GetMapping("/profit")
    fun profitQuarter() {
        profitJob.profit()
    }

    @PostMapping("/upload/dt")
    fun uploadDT(request: MultipartHttpServletRequest): String {
        val list = mutableListOf<CapitalDT>()
        try {
            val file = request.getFiles("file")[0]
            val date = file.originalFilename?.substring(0, 10) ?: ""
            val document = PDDocument.load(file.inputStream)
            val textStripper = PDFTextStripper()
            val content = textStripper.getText(document)
            val patten: Pattern = Pattern.compile(Constants.STOCK_NO_REG_PATTERN)
            val matcher: Matcher = patten.matcher(content) // 指定要匹配的字符串
            while (matcher.find()) {
                val code = matcher.group()
                val info = stockInfoPer.findByStockNoOrNull(code)
                val dt = CapitalDT(
                        code = code,
                        date = date,
                        industry = info?.industry,
                        codeName = info?.codeName
                )
                list.add(dt)
            }
            capitalDTPersistenceService.saveAll(list)
            logger.info("BaoStockController#uploadDT date = $date, size = ${list.size}")
        } catch (e: Exception) {
            logger.error("BaoStockController#error", e)
        }
        return list.stream().map { it.code }.toList().toString()
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(BaoStockController::class.java)
    }
}