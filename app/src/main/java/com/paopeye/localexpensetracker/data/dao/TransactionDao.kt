package com.paopeye.localexpensetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.paopeye.localexpensetracker.data.model.Category
import com.paopeye.localexpensetracker.data.model.CategoryChild
import com.paopeye.localexpensetracker.data.model.TransactionDetail
import com.paopeye.localexpensetracker.data.model.TransactionHeader
import com.paopeye.localexpensetracker.data.model.relation.CategoryWithChild

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: TransactionHeader)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransactionDetail(transactionDetail: TransactionDetail)

    @Update
    suspend fun updateTransaction(transaction: TransactionHeader)
    @Update
    suspend fun updateTransactionDetail(transactionDetail: TransactionDetail)

    @Query("SELECT * FROM transaction_table ORDER BY transactionHeaderId ASC")
    fun getTransactionHeader() : LiveData<List<TransactionHeader>>

    @Query("SELECT * FROM transaction_detail_table ORDER BY dLastUpdated DESC")
    fun getTransactionDetail() : LiveData<List<TransactionDetail>>

}