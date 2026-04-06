package com.example.vaultx.Util

import com.example.vaultx.Util.AppConstants.EncryptionConstants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatter {

    fun formatDate(time: Long): String {
        val sdf = SimpleDateFormat(EncryptionConstants.DATE_FORMAT, Locale.getDefault())
        return sdf.format(Date(time))
    }
}