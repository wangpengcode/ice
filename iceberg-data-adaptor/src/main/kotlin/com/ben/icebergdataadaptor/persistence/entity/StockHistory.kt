package com.ben.icebergdataadaptor.persistence.entity

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigInteger
import javax.persistence.*

@Entity
@Table(name = "STOCK_INFO")
@EntityListeners(AuditingEntityListener::class)
data class StockHistory(
	/** '主键' **/
	@Id
	@Column(name = "id", length = 64)
	val id: String,
	
	/** 交易所行情日期**/
	@Column(name = "date", length = 20)
	val date: String,
	
	/** 证券代码 **/
	@Column(name = "code", length = 20)
	val code: BigInteger,
	
	/** 开盘价 **/
	@Column(name = "open", length = 20)
	val open: String,
	
	/** 最高价 **/
	@Column(name = "high", length = 20)
	val high: String,
	
	/** 最低价 **/
	@Column(name = "low", length = 20)
	val low: String,
	
	/** 收盘价 **/
	@Column(name = "close", length = 20)
	val close: String,
	
	/** 前收盘价 **/
	@Column(name = "preclose", length = 20)
	val preclose: String,
	
	/** 成交量（累计 单位：股） **/
	@Column(name = "volume", length = 20)
	val volume: String,
	
	/** 成交额（单位：人民币元） **/
	@Column(name = "amount", length = 20)
	val amount: String,
	
	/** 复权状态(1：后复权， 2：前复权，3：不复权） **/
	@Column(name = "adjustflag", length = 20)
	val adjustflag: String,
	
	/** 换手率 **/
	@Column(name = "turn", length = 20)
	val turn: String,
	
	/** 交易状态(1：正常交易 0：停牌） **/
	@Column(name = "tradestatus", length = 20)
	val tradestatus: String,
	
	/** '涨跌幅（百分比） **/
	@Column(name = "pctChg", length = 20)
	val pctChg: String,
	
	/** 滚动市盈率 **/
	@Column(name = "peTTM", length = 20)
	val peTTM: String,
	
	/** 市净率 **/
	@Column(name = "pbMRQ", length = 20)
	val pbMRQ: String,
	
	/** 滚动市销率 **/
	@Column(name = "psTTM", length = 20)
	val psTTM: String,
	
	/** 滚动市现率 **/
	@Column(name = "pcfNcfTTM", length = 20)
	val pcfNcfTTM: String,
	
	/** 是否ST股，1是，0否**/
	@Column(name = "isST", length = 20)
	val isST: String? = null
) {
	override fun toString(): String {
		return "StockHistory(id='$id', date='$date', code=$code, open='$open', high='$high', low='$low', close='$close', preclose='$preclose', volume='$volume', amount='$amount', adjustflag='$adjustflag', turn='$turn', tradestatus='$tradestatus', pctChg='$pctChg', peTTM='$peTTM', pbMRQ='$pbMRQ', psTTM='$psTTM', pcfNcfTTM='$pcfNcfTTM', isST=$isST)"
	}
}