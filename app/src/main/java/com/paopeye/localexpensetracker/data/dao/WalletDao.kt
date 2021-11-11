package com.paopeye.localexpensetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.paopeye.localexpensetracker.data.model.User
import com.paopeye.localexpensetracker.data.model.Wallet

@Dao
interface WalletDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWallet(wallet: Wallet)

    @Update
    suspend fun updateWallet(wallet: Wallet)

    @Delete
    suspend fun deleteWallet(wallet: Wallet)

    @Query("SELECT * FROM wallet_table ORDER BY dCreated desc")
    fun readWalletData() : LiveData<List<Wallet>>

    @Query("SELECT COUNT(sWalletName) FROM wallet_table ORDER BY sWalletName ASC")
    fun isWalletExist(): LiveData<Boolean>
}