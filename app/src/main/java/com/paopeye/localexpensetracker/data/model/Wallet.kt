package com.paopeye.localexpensetracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.paopeye.localexpensetracker.data.typeconverter.CalendarConverter
import java.util.*

@Entity(tableName = "wallet_table")
data class Wallet(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val sWalletName: String,
    val fWalletBalance: Double,
    var iWalletColor: Int,
    var dCreated:Long,
    var dLastUpdated:Long
)