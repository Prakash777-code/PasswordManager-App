package com.example.vaultx.Util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatter {

    fun formatDate(time: Long): String {
        val sdf = SimpleDateFormat(AppConstants.DATE_FORMAT, Locale.getDefault())
        return sdf.format(Date(time))
    }
}