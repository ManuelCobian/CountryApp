package com.luvsoft.base.utils

import com.luvsoft.base.extensions.triLet
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

object DateUtil {


    const val EMPTY_TAG_CON = ""

    var format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var formatMonth = SimpleDateFormat("dd/MMM", Locale.getDefault())
    var formatServer = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    fun String.formatDateMount(): String {
        val inputFormat = formatServer
        val outputFormat = format

        val date = inputFormat.parse(this)
        return outputFormat.format(date)
    }

    fun getCurrentDate(): String {
        return formatServer.format(Date())
    }

    fun getFormattedTime(seconds: Int): String {
        val minutes = seconds / 60
        abs(minutes)
        val secondsLeft = seconds % 60

        if (seconds == 0) return "-:-"

        val builder = StringBuilder()

        if (minutes > 0) builder.append("${minutes}m")
        if (secondsLeft > 0) builder.append("${secondsLeft}s")

        return builder.toString()
    }

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    fun getHourFormatFromDouble(minutes: Double): String {
        try {
            var segTotal = minutes * 60
            var minFinish = (segTotal / 60).toInt()
            var segFinish = (segTotal % 60).toInt()
            var toShow = ""
            if (minFinish > 0) {
                toShow = "$minFinish min"
                if (segFinish > 0) {
                    toShow = "$toShow $segFinish seg"
                }
            } else {
                toShow = "$segFinish seg"
            }
            return toShow
        } catch (e: Exception) {
            return ""
        }
    }

    fun formatDateCero(date: String): String {
        if (date.isNotEmpty()) {
            val splitDate = date.split("/")
            val newDate =
                splitDate[2] + "-" + splitDate[1] + "-" + splitDate.get(0)
            return newDate
        } else {
            return EMPTY_TAG_CON
        }
    }

    fun getCurrentDateTimeDayOfMonth(dayOfMont: Int): Date {
        val calenderTime = Calendar.getInstance()
        calenderTime.add(Calendar.DAY_OF_MONTH, dayOfMont)
        val currentDateTime = format.format(calenderTime.time)
        return format.parse(currentDateTime)
    }

    fun addDayForTodayMonth(dayOfMont: Int): String {
        val calenderTime = Calendar.getInstance()
        calenderTime.add(Calendar.DAY_OF_MONTH, dayOfMont)

        return format.format(calenderTime.time)
    }

    fun addDayForTodayYear(dayOfMont: Int): String {
        val calenderTime = Calendar.getInstance()
        calenderTime.add(Calendar.DAY_OF_MONTH, dayOfMont)

        return format.format(calenderTime.time)
    }

    fun getSimplifiedDate(date: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(DateUtil.getCurrentDateTimeDayOfMonth(date))
    }

    fun checkCurrentTimeIsBetweenGivenString(): Boolean {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val startDate: Date? = simpleDateFormat.parse("2024-06-16T00:00:00.000Z")
        val endDate: Date? = simpleDateFormat.parse("2024-06-16T23:59:59.599Z")
        val currentDateFormat = simpleDateFormat.format(Date())
        val currentDate = simpleDateFormat.parse(currentDateFormat)

        return Triple(startDate, endDate, currentDate).triLet { start, end, current ->
            current in start..end
        } ?: run { false }
    }
}