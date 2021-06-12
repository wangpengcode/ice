package com.ben.icebergdataadaptor.persistence.entity

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigInteger
import javax.persistence.*

/**
 * 参数名称	参数描述	算法说明
 * code	证券代码
 * pubDate	公司发布财报的日期
 * statDate	财报统计的季度的最后一天, 比如2017-03-31, 2017-06-30
 * NRTurnRatio	应收账款周转率(次)	营业收入/[(期初应收票据及应收账款净额+期末应收票据及应收账款净额)/2]
 * NRTurnDays	应收账款周转天数(天)	季报天数/应收账款周转率(一季报：90天，中报：180天，三季报：270天，年报：360天)
 * INVTurnRatio	存货周转率(次)	营业成本/[(期初存货净额+期末存货净额)/2]
 * INVTurnDays	存货周转天数(天)	季报天数/存货周转率(一季报：90天，中报：180天，三季报：270天，年报：360天)
 * CATurnRatio	流动资产周转率(次)	营业总收入/[(期初流动资产+期末流动资产)/2]
 * AssetTurnRatio	总资产周转率	营业总收入/[(期初资产总额+期末资产总额)/2]
 */
@Entity
@Table(name = "STOCK_QUARTER_PROFIT")
@EntityListeners(AuditingEntityListener::class)
data class ProfitQuarter(
	@Id
	@GeneratedValue
	@Column(name = "id", length = 64)
	val id: BigInteger? = null,
	
	@Column(name = "code", length = 64)
	var code: String? = null,
	
	@Column(name = "pub_date", length = 64)
	var pubDate: String? = null,
	
	@Column(name = "stat_date", length = 64)
	var statDate: String? = null,
	
	@Column(name = "nr_turn_ratio", length = 64)
	var NRTurnRatio: String? = null,
	
	@Column(name = "nr_turn_days", length = 64)
	var NRTurnDays: String? = null,
	
	@Column(name = "inv_turn_ratio", length = 64)
	var INVTurnRatio: String? = null,
	
	@Column(name = "inv_turn_days", length = 64)
	var INVTurnDays: String? = null,
	
	@Column(name = "ca_turn_ratio", length = 64)
	var CATurnRatio: String? = null,
	
	@Column(name = "asset_turn_ratio", length = 64)
	var AssetTurnRatio: String? = null,
)