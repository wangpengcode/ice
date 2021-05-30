package com.ben.analysis.persistence.entity

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigInteger
import javax.persistence.*

@Entity
@Table(name = "SUPER_STAR")
@EntityListeners(AuditingEntityListener::class)
data class SuperStar(
        @Id
        @GeneratedValue
        @Column(name = "id", length = 64)
        val id: BigInteger? = null,

        @Column(name = "code_with_ex", length = 64)
        val code_with_ex: String,

        @Column(name = "date", length = 64)
        val date: String,

        @Column(name = "year", length = 64)
        val year: String,

        @Column(name = "month", length = 64)
        val month: String,

        @Column(name = "code_name", length = 64)
        var code_name: String? = null,

        @Column(name = "industry", length = 64)
        var industry: String? = null,

        @Column(name = "zt", length = 10)
        var zt: Boolean = false,

        @Column(name = "dt", length = 10)
        var dt: Boolean = false,

        @Column(name = "change", length = 64)
        var change: String? = null
)