package com.paopeye.localexpensetracker.data.typeconverter

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {
    @TypeConverter
    fun toCalendar(l: Long?): Calendar? {
        val c: Calendar = Calendar.getInstance()
        if (l != null) {
            c.timeInMillis = l
        }
        return c
    }

    @TypeConverter
    fun fromCalendar(c: Calendar?): Long? {
        return if (c == null) null else c.getTime().getTime()
    }
}