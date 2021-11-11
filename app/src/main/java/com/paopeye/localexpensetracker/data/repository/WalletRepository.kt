package com.paopeye.localexpensetracker.data.repository

import androidx.lifecycle.LiveData
import com.paopeye.localexpensetracker.data.dao.WalletDao
import com.paopeye.localexpensetracker.data.model.Wallet

class WalletRepository (private val walletDao: WalletDao) {
    val readWalletData: LiveData<List<Wallet>> =walletDao.readWalletData()
    val isWalletExist: LiveData<Boolean> =walletDao.isWalletExist()

    suspend fun addWallet(wallet: Wallet){
        walletDao.addWallet(wallet)
    }

    suspend fun updateWallet(wallet: Wallet){
        walletDao.updateWallet(wallet)
    }

    suspend fun deleteWallet(wallet: Wallet){
        walletDao.deleteWallet(wallet)
    }
}