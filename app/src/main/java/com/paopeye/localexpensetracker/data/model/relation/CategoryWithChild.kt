package com.paopeye.localexpensetracker.data.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.paopeye.localexpensetracker.data.model.Category
import com.paopeye.localexpensetracker.data.model.CategoryChild

data class CategoryWithChild(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "catId",
        entityColumn = "catId"
    )
    val childCat :List<CategoryChild>
)