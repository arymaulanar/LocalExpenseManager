package com.paopeye.localexpensetracker.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.paopeye.localexpensetracker.data.database.UserDatabase
import com.paopeye.localexpensetracker.data.model.User
import com.paopeye.localexpensetracker.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(application: Application): AndroidViewModel(application) {
    val readUserData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository= UserRepository(userDao)
        readUserData = repository.readUserData
    }

    fun updateUser(user: User){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateUser(user)
        }
    }
}