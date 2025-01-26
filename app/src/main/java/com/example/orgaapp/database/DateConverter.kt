package com.example.orgaapp.database

import android.icu.text.SimpleDateFormat
import androidx.room.TypeConverter
import java.util.Date
import java.util.Locale

class DateConverter {
    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    @TypeConverter
    fun fromDate(date: Date?): String? {
        return date?.let{dateFormat.format(it)}
    }

    @TypeConverter
    fun toDate(dateString: String?): Date? {
        return dateString?.let{dateFormat.parse(it)}
    }
}