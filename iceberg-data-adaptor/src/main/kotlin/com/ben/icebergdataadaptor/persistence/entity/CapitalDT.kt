package com.ben.icebergdataadaptor.persistence.entity

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigInteger
import javax.persistence.*

@Entity
@Table(name = "CAPITAL_DT")
@EntityListeners(AuditingEntityListener::class)
data class CapitalDT (
	@Id
	@GeneratedValue
	@Column(name = "ID", length = 64)
	val id: BigInteger? = null,
	
	@Column(name = "CODE", length = 64)
	var code: String,
	
	@Column(name = "DATE", length = 64)
	var date: String,
	
	@Column(name = "CODE_NAME", length = 64)
	var codeName: String? = null,
	
	@Column(name = "INDUSTRY", length = 64)
	var industry: String? = null,
	
	@Column(name = "REMARK", length = 64)
	var remark: String? = null,
	
	@Column(name = "LAST_MONTH", length = 64)
	var lastMonth:  BigInteger? = null,
	
	@Column(name = "LAST_TWO_MONTH", length = 64)
	var lastTwoMonth:  BigInteger? = null,
	
	@Column(name = "LAST_THREE_MONTH", length = 64)
	var lasThreeMonth:  BigInteger? = null,
	
	@Column(name = "LAST_HALF_YEAR", length = 64)
	var lastHalfYear:  BigInteger? = null
)