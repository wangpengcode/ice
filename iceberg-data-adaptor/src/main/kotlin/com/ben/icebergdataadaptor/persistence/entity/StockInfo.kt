package com.ben.icebergdataadaptor.persistence.entity

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "STOCK_INFO")
@EntityListeners(AuditingEntityListener::class)
data class StockInfo(
	@Id
	@Column(name = "stock_no", length = 64)
	val stockNo: String,
	
	/** **/
	@Column(name = "exchange_house", length = 10)
	val exchangeHouse: String,
	
	/** **/
	@Column(name = "status", length = 20)
	val status: String? = null,
	
	/** **/
	@Column(name = "download_times", length = 20)
	var downloadTimes: String? = null,
	
	/** **/
	@Column(name = "have_data_times", length = 20)
	var haveDataTimes: String? = null
){
	fun haveDataRate(): BigDecimal {
		return downloadTimes?.let {
			BigDecimal(haveDataTimes ?: "0").divide(it.toBigDecimal())
		} ?: BigDecimal.ZERO
	}
}
