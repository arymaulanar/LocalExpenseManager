package com.paopeye.localexpensetracker.data.repository

import androidx.lifecycle.LiveData
import com.paopeye.localexpensetracker.data.dao.UserDao
import com.paopeye.localexpensetracker.data.model.User

class UserRepository(private val userDao: UserDao) {
    val readUserData:LiveData<List<User>> =userDao.readUserData()
    val isUserExist:LiveData<Boolean> =userDao.isUserExist()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

}