package com.paopeye.localexpensetracker.data.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.paopeye.localexpensetracker.data.database.UserDatabase
import com.paopeye.localexpensetracker.data.model.Category
import com.paopeye.localexpensetracker.data.model.CategoryChild
import com.paopeye.localexpensetracker.data.model.User
import com.paopeye.localexpensetracker.data.model.relation.CategoryWithChild
import com.paopeye.localexpensetracker.data.repository.CategoryRepository
import com.paopeye.localexpensetracker.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository
    private val categoryRepository: CategoryRepository

    val readCategoryWithChildByName = MediatorLiveData<List<CategoryWithChild>>()
    val readUserData: LiveData<List<User>>
    val readCategoryWithChild: LiveData<List<CategoryWithChild>>

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        val categoryDao = UserDatabase.getDatabase(application).categoryDao()
        userRepository = UserRepository(userDao)
        categoryRepository = CategoryRepository(categoryDao)

        readUserData = userRepository.readUserData
        readCategoryWithChild = categoryRepository.getCategoryWithChild
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUser(user)
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.updateCategory(category)
        }
    }

    fun updateChildCategory(categoryChild: CategoryChild) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.updateChildCategory(categoryChild)
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.addCategory(category)
        }
    }

    fun addChildCategory(categoryChild: CategoryChild) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.addChildCategory(categoryChild)
        }
    }

    fun saveParentCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            if (categoryRepository.isCategoryExist(category.catId) == 0) {
                addCategory(category)
            } else {
                updateCategory(category)
            }
        }

    }

    fun saveChildCategory(childCategoryChild: CategoryChild) {
        viewModelScope.launch(Dispatchers.IO) {
            if (categoryRepository.isChildCategoryExist(childCategoryChild.catChildId,childCategoryChild.catId) == 0) {
                addChildCategory(childCategoryChild)
            } else {
                updateChildCategory(childCategoryChild)
            }
        }

    }
    fun getCategoryWithChild(sCategoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            readCategoryWithChildByName.value =
                categoryRepository.getCategoryWithChildByName(sCategoryName)
        }
    }
}