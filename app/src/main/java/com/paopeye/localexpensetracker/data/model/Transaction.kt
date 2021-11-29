package com.paopeye.localexpensetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transaction_table")
data class TransactionHeader (
    @PrimaryKey(autoGenerate = true)
    val transactionHeaderId:Int,
    val walletId:Int,
    val catId:Int,
    var dCreated:Long,
    var dLastUpdated:Long
)


@Entity(tableName = "transaction_detail_table")
data class TransactionDetail(
    @PrimaryKey(autoGenerate = true)
    val transactionDetailId:Int,
    val transactionHeaderId: Int,
    val sCategoryChildName:String,
    val sTransactionName:String,
    val fTransactionName: Double,
    val dTransactionDate:Long,
    val sWalletName:String,
    val iWalletColor: Int,
    var dCreated:Long,
    var dLastUpdated:Long
)