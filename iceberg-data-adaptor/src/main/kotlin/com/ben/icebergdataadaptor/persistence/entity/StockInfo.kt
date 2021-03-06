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
	var exchangeHouse: String,
	
	/** **/
	@Column(name = "status", length = 20)
	val status: String? = null,
	
	/** **/
	@Column(name = "download_times", length = 20)
	var downloadTimes: String? = null,
	
	/** **/
	@Column(name = "have_data_times", length = 20)
	var haveDataTimes: String? = null,
	
	/** 证券名称 **/
	@Column(name = "code_name", length = 60)
	var codeName: String? = null,
	
	/** 上市日期 **/
	@Column(name = "ipo_date", length = 20)
	var ipoDate: String? = null,
	
	/** 退市日期 **/
	@Column(name = "out_date", length = 20)
	var outDate: String? = null,
	
	/** 证券类型，其中1：股票，2：指数,3：其它 **/
	@Column(name = "type", length = 20)
	var type: String? = null,
	
	/** 上市状态，其中1：上市，0：退市 **/
	@Column(name = "ipo_status", length = 20)
	var ipoStatus: String? = null,

	/** 所属行业 **/
	@Column(name = "industry", length = 60)
	var industry: String? = null,

	/** 所属行业类别 **/
	@Column(name = "industry_classification", length = 60)
	var industryClassification: String? = null,

	/** 原始代码 **/
	@Column(name = "code_with_exchange", length = 20)
	var codeWithEx: String? = null,

	/** 原始代码 **/
	@Column(name = "last_update_date", length = 20)
	var lastUpdateDate: String? = null,
	
	
	@Column(name = "is_download", length = 10)
	var isDownload: Boolean = false,
	
	@Column(name = "last_download_at", length = 10)
	var lastDownloadAt: String? = null,
){
	fun haveDataRate(): BigDecimal {
		return downloadTimes?.let {
			BigDecimal(haveDataTimes ?: "0").divide(it.toBigDecimal())
		} ?: BigDecimal.ZERO
	}
}
