package com.paopeye.localexpensetracker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paopeye.localexpensetracker.data.dao.CategoryDao
import com.paopeye.localexpensetracker.data.dao.WalletDao
import com.paopeye.localexpensetracker.data.model.Category
import com.paopeye.localexpensetracker.data.model.CategoryChild
import com.paopeye.localexpensetracker.data.model.Wallet
import com.paopeye.localexpensetracker.data.model.relation.CategoryWithChild

class CategoryRepository (private val categoryDao: CategoryDao) {
    val getCategory: LiveData<List<Category>> =categoryDao.getCategoryData()
    val getChildCategory: LiveData<List<CategoryChild>> =categoryDao.getChildCategoryData()
    val getCategoryWithChild: LiveData<List<CategoryWithChild>> =categoryDao.getCategoryWithChild()

    suspend fun addCategory(category: Category){
        categoryDao.addCategory(category)
    }
    suspend fun addChildCategory(categoryChild: CategoryChild){
        categoryDao.addCategoryChild(categoryChild)
    }
    suspend fun updateCategory(category: Category){
        categoryDao.updateCategory(category)
    }
    suspend fun updateChildCategory(categoryChild: CategoryChild){
        categoryDao.updateCategoryChild(categoryChild)
    }
    suspend fun getCategoryWithChildByName(sCategoryName:String):List<CategoryWithChild>{
        return categoryDao.getCategoryWithChildByName(sCategoryName)
    }
    fun isCategoryExist(catId: Int):Int{
        return categoryDao.getCountCategoryDataById(catId)
    }
    fun isChildCategoryExist(catId: Int,catParentId:Int):Int{
        return categoryDao.getCountChildCategoryDataById(catId,catParentId)
    }
}