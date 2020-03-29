package com.sabgil.contena.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val contenaServerDateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.KOREA)

    private val hangulDateFormat = SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA)

    fun parseContenaToHangul(contenaDate: String): String {
        try {
            return contenaServerDateFormat.parse(contenaDate)?.let {
                hangulDateFormat.format(it)
            } ?: ""
        } catch (e: ParseException) {
            e.printStackTrace()
            throw IllegalArgumentException("Invalid Contena Server Format parameter: $contenaDate")
        }
    }
}