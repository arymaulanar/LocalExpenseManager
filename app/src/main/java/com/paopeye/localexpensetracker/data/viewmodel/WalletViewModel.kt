package com.paopeye.localexpensetracker.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.paopeye.localexpensetracker.data.database.UserDatabase
import com.paopeye.localexpensetracker.data.model.User
import com.paopeye.localexpensetracker.data.model.Wallet
import com.paopeye.localexpensetracker.data.repository.UserRepository
import com.paopeye.localexpensetracker.data.repository.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WalletViewModel(application: Application): AndroidViewModel(application) {
    val readWalletData: LiveData<List<Wallet>>
    val isWalletExist: LiveData<Boolean>
    private val repository: WalletRepository
    init {
        val walletDao = UserDatabase.getDatabase(application).walletDao()
        repository= WalletRepository(walletDao)
        readWalletData = repository.readWalletData
        isWalletExist = repository.isWalletExist
    }
    fun updateWallet(wallet: Wallet){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateWallet(wallet)
        }
    }
    fun addWallet(wallet: Wallet){
        viewModelScope.launch (Dispatchers.IO){
            repository.addWallet(wallet)
        }
    }
    fun deleteWallet(wallet: Wallet){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteWallet(wallet)
        }
    }
}