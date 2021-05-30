package com.ben.analysis.persistence.repository

import com.ben.analysis.persistence.entity.SuperStar
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface SuperStarRepository : CrudRepository<SuperStar, String> {
    @Query("select max(a.date) from SuperStar a where a.code_with_ex = ?1")
    fun queryTheNewestDay(codeWithEx: String): String?
}