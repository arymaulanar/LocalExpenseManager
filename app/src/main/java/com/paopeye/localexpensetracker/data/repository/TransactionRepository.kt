package com.paopeye.localexpensetracker.data.repository

import androidx.lifecycle.LiveData
import com.paopeye.localexpensetracker.data.dao.TransactionDao
import com.paopeye.localexpensetracker.data.model.TransactionDetail
import com.paopeye.localexpensetracker.data.model.TransactionHeader

class TransactionRepository(private val transactionDao: TransactionDao) {
    val getAllTransactionHeader: LiveData<List<TransactionHeader>> =transactionDao.getTransactionHeader()
    val getAllTransactionDetail: LiveData<List<TransactionDetail>> =transactionDao.getTransactionDetail()
}