package com.ben.icebergdataadaptor.transfer

import com.ben.icebergdataadaptor.extensions.decode
import com.ben.icebergdataadaptor.extensions.removeIllegalChar
import com.ben.icebergdataadaptor.extensions.toNakedCode
import com.ben.icebergdataadaptor.persistence.entity.ProfitQuarter
import com.ben.icebergdataadaptor.persistence.entity.StockHistory
import com.ben.icebergdataadaptor.persistence.entity.StockInfo
import com.ben.icebergdataadaptor.webhook.ReceiveController
import java.time.LocalDate
import javax.persistence.Column

/**
 * The order should mapping with the data of the python api.
 * **/
fun List<String>.toStockHistory() = StockHistory(
	date = this[0],
	code = this[1].replace("sh.", "")
		.replace("sz.", "")
		.removeIllegalChar().toBigInteger(),
	stockNo = this[1],
	open = this[2],
	high = this[3],
	low = this[4],
	close = this[5],
	preclose = this[6],
	volume = this[7],
	amount = this[8],
	adjustflag = this[9],
	turn = this[10],
	tradestatus = this[11],
	pctChg = this[12],
	peTTM = this[13],
	pbMRQ = this[14],
	psTTM = this[15],
	pcfNcfTTM = this[16],
	isST = this[17].removeIllegalChar()
)

fun List<String>.toStockInfo(): StockInfo = StockInfo(
	stockNo = this[0].removeIllegalChar().toNakedCode(),
	exchangeHouse = this[0].split(".")[0].removeIllegalChar().trim(),
	codeName = try {
		decode(this[1].removeIllegalChar())
	} catch (e: Exception) {
		""
	},
	ipoDate = this[2].removeIllegalChar(),
	outDate = this[3].removeIllegalChar(),
	type = this[4].removeIllegalChar(),
	ipoStatus = this[5].removeIllegalChar()
).apply { codeWithEx = "$exchangeHouse.$stockNo" }

fun List<String>.toIndustry(): ReceiveController.Industry = ReceiveController.Industry(
	date = this[0].removeIllegalChar(),
	code = this[1].removeIllegalChar(),
	codeName = decode(this[2].removeIllegalChar()),
	industry = decode(this[3].removeIllegalChar()),
	industryClassify = decode(this[4].removeIllegalChar())
)

fun ReceiveController.Industry.toStockInfo() = StockInfo(
	stockNo = code.split(".")[1],
	exchangeHouse = code.split(".")[0],
	codeName = codeName,
	industry = industry,
	industryClassification = industryClassify,
	codeWithEx = code,
	lastUpdateDate = LocalDate.now().toString()
)

fun List<String>.toProfitQuarter() = ProfitQuarter(
	code = this[0].removeIllegalChar(),
	pubDate = this[1].removeIllegalChar(),
	statDate = this[2].removeIllegalChar(),
	NRTurnRatio = this[3].removeIllegalChar(),
	NRTurnDays = this[4].removeIllegalChar(),
	INVTurnRatio = this[5].removeIllegalChar(),
	INVTurnDays = this[6].removeIllegalChar(),
	CATurnRatio = this[7].removeIllegalChar(),
	AssetTurnRatio = this[8].removeIllegalChar()
)