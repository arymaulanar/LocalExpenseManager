package com.paopeye.localexpensetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.paopeye.localexpensetracker.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user:User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readUserData() : LiveData<List<User>>

    @Query("SELECT COUNT(id) FROM user_table ORDER BY id ASC")
    fun isUserExist(): LiveData<Boolean>
}