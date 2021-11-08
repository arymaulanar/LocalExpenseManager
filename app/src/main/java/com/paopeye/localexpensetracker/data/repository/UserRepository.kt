package com.paopeye.localexpensetracker.data.repository

import androidx.lifecycle.LiveData
import com.paopeye.localexpensetracker.data.dao.UserDao
import com.paopeye.localexpensetracker.data.model.User

class UserRepository(private val userDao: UserDao) {
    val readUserData:LiveData<List<User>> =userDao.readUserData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
}