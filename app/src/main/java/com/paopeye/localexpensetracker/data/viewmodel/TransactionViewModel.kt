package com.paopeye.localexpensetracker.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.paopeye.localexpensetracker.data.database.UserDatabase
import com.paopeye.localexpensetracker.data.model.relation.CategoryWithChild
import com.paopeye.localexpensetracker.data.repository.CategoryRepository
import com.paopeye.localexpensetracker.data.repository.UserRepository

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryRepository: CategoryRepository
    val readCategoryWithChild: LiveData<List<CategoryWithChild>>

    init {
        val categoryDao = UserDatabase.getDatabase(application).categoryDao()
        categoryRepository = CategoryRepository(categoryDao)
        readCategoryWithChild = categoryRepository.getCategoryWithChild
    }
}