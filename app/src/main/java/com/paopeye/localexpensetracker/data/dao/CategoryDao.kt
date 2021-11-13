package com.paopeye.localexpensetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.paopeye.localexpensetracker.data.model.Category
import com.paopeye.localexpensetracker.data.model.CategoryChild
import com.paopeye.localexpensetracker.data.model.relation.CategoryWithChild

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategoryChild(child: CategoryChild)

    @Update
    suspend fun updateCategory(category: Category)
    @Update
    suspend fun updateCategoryChild(child: CategoryChild)

    @Transaction
    @Query("SELECT * FROM category_table ")
    fun getCategoryWithChild():LiveData<List<CategoryWithChild>>

    @Transaction
    @Query("SELECT * FROM category_table WHERE sCategoryName = :sCategoryName")
    suspend fun getCategoryWithChildByName(sCategoryName:String):List<CategoryWithChild>

    @Query("SELECT * FROM category_table ORDER BY sCategoryName ASC")
    fun getCategoryData() : LiveData<List<Category>>

    @Query("SELECT * FROM category_child_table ORDER BY sCategoryChildName ASC")
    fun getChildCategoryData() : LiveData<List<CategoryChild>>

    @Query("SELECT COUNT(*) FROM category_table WHERE catId = :catId")
    fun getCountCategoryDataById(catId:Int) : Int

    @Query("SELECT COUNT(*) FROM category_child_table WHERE catChildId= :catChildId and catId = :catParentId")
    fun getCountChildCategoryDataById(catChildId:Int,catParentId:Int) : Int
}