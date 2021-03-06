package com.ben.analysis.persistence.entity

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

		/** 原始代码 **/
		@Column(name = "last_days_strong_5", length = 40)
		var lastDaysStrong5: String? = null,

		/** 原始代码 **/
		@Column(name = "last_days_strong_10", length = 40)
		var lastDaysStrong10: String? = null,

		/** 原始代码 **/
		@Column(name = "last_days_strong_20", length = 40)
		var lastDaysStrong20: String? = null,

		/** 100 日下影线个数 **/
		@Column(name = "last_days_blow_shadow_100", length = 40)
		var lastDaysBlowShadow100: String? = null,

		/** 50 日下影线个数 **/
		@Column(name = "last_days_blow_shadow_50", length = 40)
		var lastDaysBlowShadow50: String? = null,

		/** 15 日下影线个数 **/
		@Column(name = "last_days_blow_shadow_15", length = 40)
		var lastDaysBlowShadow15: String? = null

		){
	fun haveDataRate(): BigDecimal {
		return downloadTimes?.let {
			BigDecimal(haveDataTimes ?: "0").divide(it.toBigDecimal())
		} ?: BigDecimal.ZERO
	}
}
