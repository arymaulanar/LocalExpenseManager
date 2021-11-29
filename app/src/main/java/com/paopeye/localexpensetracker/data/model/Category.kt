package com.paopeye.localexpensetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category (
    @PrimaryKey(autoGenerate = true)
    val catId:Int,
    val sCategoryName:String
)


@Entity(tableName = "category_child_table")
data class CategoryChild(
    @PrimaryKey(autoGenerate = true)
    val catChildId:Int,
    val catId: Int,
    val sCategoryChildName:String
)