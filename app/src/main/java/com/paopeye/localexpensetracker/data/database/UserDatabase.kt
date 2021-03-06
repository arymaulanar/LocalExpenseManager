package com.paopeye.localexpensetracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.paopeye.localexpensetracker.data.dao.CategoryDao
import com.paopeye.localexpensetracker.data.dao.UserDao
import com.paopeye.localexpensetracker.data.dao.WalletDao
import com.paopeye.localexpensetracker.data.model.*
import com.paopeye.localexpensetracker.data.model.relation.CategoryWithChild

@Database(entities = [
    User::class,
    Wallet::class,
    Category::class,
    CategoryChild::class]
    , version = 4, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao():UserDao
    abstract fun walletDao():WalletDao
    abstract fun categoryDao():CategoryDao

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? =null

        fun getDatabase(context: Context):UserDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null)
                return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    UserDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}