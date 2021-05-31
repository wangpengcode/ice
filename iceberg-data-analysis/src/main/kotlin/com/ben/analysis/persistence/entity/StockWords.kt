package com.ben.analysis.persistence.entity

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigInteger
import javax.persistence.*

@Entity
@Table(name = "STOCK_WORDS")
@EntityListeners(AuditingEntityListener::class)
data class StockWords(
        @Id
        @GeneratedValue
        @Column(name = "id", length = 64)
        val id: BigInteger? = null,

        @Column(name = "code_with_ex", length = 64)
        val code_with_ex: String,

        @Column(name = "date", length = 64)
        val date: String,

        @Column(name = "code_name", length = 64)
        var code_name: String? = null,

        @Column(name = "industry", length = 64)
        var industry: String? = null,

        @Column(name = "lowest", length = 64)
        var lowest: String? = null,

        @Column(name = "words_is_valid", length = 10)
        var words_is_valid: Boolean = true,

        @Column(name = "is_st", length = 10)
        var is_st: Boolean = false,

        @Column(name = "out_standing", length = 64)
        var out_standing: String? = null,

        @Column(name = "last_day", length = 64)
        var last_day: BigInteger? = null
)