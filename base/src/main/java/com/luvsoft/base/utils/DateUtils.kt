package com.luvsoft.base.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val MILLISECONDS_TOTAL = 1000
const val MINUTES_TOTAL = 60

fun isoTo12HourFormat(stringDate: String): String {
    return SimpleDateFormat("hh:mm a", Locale.US)
        .format(parseIsoStringDate(stringDate)).toLowerCase()
}

@SuppressLint("SimpleDateFormat")
fun getHoursDate(stringDate: String): String {
    val dateNow = Calendar.getInstance()
    val dateGive = Calendar.getInstance()
    dateGive.time = parseIsoStringDate(stringDate)
    val milliseconds = dateNow.timeInMillis - dateGive.timeInMillis
    val minutes = (milliseconds / (MILLISECONDS_TOTAL * MINUTES_TOTAL)).toInt()
    val hours = (milliseconds / (MILLISECONDS_TOTAL * MINUTES_TOTAL * MINUTES_TOTAL)).toInt()
    return "$hours:$minutes"
}

fun parseIsoStringDate(stringDate: String, isUTC: Boolean = true): Date {
    return stringDate.toDate(isUTC = isUTC)
}

fun String.toDate(dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", isUTC: Boolean): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    if (isUTC) parser.timeZone = TimeZone.getTimeZone("UTC")
    return parser.parse(this)
}

fun parseToDayMonthLetterAndYear(stringDate: String, addYear: Boolean = true): String {
    return if (addYear) {
        SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
            .format(parseIsoStringDate(stringDate))
    } else {
        SimpleDateFormat("d MMMM", Locale.getDefault())
            .format(parseIsoStringDate(stringDate))
    }
}

fun parseDateFormat(date: String): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateParse = LocalDate.parse(date)
        val formatParse = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return dateParse.format(formatParse)
    } else {
        val dateParse = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateParse.format(date)
    }
}

fun parseToDayMonthYearAndHours(
    stringDate: String,
    dateFormat: String = "d/MM/yy",
    hourFormat: String = ", HH:mm"
): String =
    SimpleDateFormat("$dateFormat$hourFormat", Locale.getDefault())
        .format(parseIsoStringDate(stringDate))

fun parseToDayMonthYear(stringDate: String): String =
    SimpleDateFormat("d/MM/yy", Locale.getDefault())
        .format(parseIsoStringDate(stringDate))

fun String.parseDayAndMonthToDate(): Date? {
    return try {
        this.toDate("d MMMM yyyy", isUTC = true)
    } catch (e: ParseException) {
        null
    }
}

fun parseToDayMonthAndYearNumberFormat(stringDate: String): String =
    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        .format(parseIsoStringDate(stringDate))

const val DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"

fun getCurrentTime(): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT)
        return current.format(formatter)
    } else {
        val current = Date()
        val formatter = SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault())
        return formatter.format(current)
    }
}

fun getCurrentTimeMM(): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return current.format(formatter)
    } else {
        val current = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(current)
    }
}

fun getHoursFormat(hour: String): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val hoursConvert = LocalTime.parse(hour, DateTimeFormatter.ofPattern("HH:mm:ss.SSS"))
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        return hoursConvert.format(formatter)
    } else {
        val hoursConvert = SimpleDateFormat("hh:mm a", Locale.getDefault()).parse(hour)
        return hoursConvert?.toGMTString().orEmpty()
    }
}

const val SECONDS_DIVIDER = 1000
const val MINUTES_DIVIDER = 60

fun getTimeDifference(dateString: String, toSeconds: Boolean = false): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (toSeconds) {
            val limitDate = dateString.toDate(isUTC = true)
            val dateFormat = SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault())
            val currentDate = dateFormat.parse(getCurrentTime())
            val diff = limitDate.time - currentDate.time
            val seconds = diff / SECONDS_DIVIDER

            seconds.toString()
        } else {
            val dateFormat = SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault())
            val startDate = dateFormat.parse(dateString)
            val currentDate = dateFormat.parse(getCurrentTime())
            val diff = currentDate.time - startDate.time
            val seconds = diff / SECONDS_DIVIDER
            val minutes = seconds / MINUTES_DIVIDER

            minutes.toString()
        }
    } else {
        ""
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeDifference(dateString: Date, toSeconds: Boolean = false): Long {
    val dateFormat = SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault())
    val currentDate = dateFormat.parse(getCurrentTime())
    val diff = currentDate.time - dateString.time
    val seconds = diff / SECONDS_DIVIDER
    val minutes = seconds / MINUTES_DIVIDER
    if (toSeconds) {
        return seconds
    } else {
        return minutes
    }
}

private const val SECONDS_OP = 3600
private const val MINUTES_OP = 60

fun toHourFormat(totalSecs: Long): Triple<Long, Long, Long> {
    val hours = totalSecs / SECONDS_OP
    val minutes = (totalSecs % SECONDS_OP) / MINUTES_OP
    val seconds = totalSecs % MINUTES_OP
    return Triple(hours, minutes, seconds)
}

fun getUTCDate(date: String, hour: String): String {
    val currentDateFormat = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.US)
    val currentDate = "$date $hour"
    val convertedDate: Date? = currentDateFormat.parse(currentDate)
    return convertedDate?.toUTC().orEmpty()
}

fun Date.toUTC(): String {
    val newDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    newDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return newDateFormat.format(this)
}

private const val FORMAT_HOUR = "%02d:%02d %s"
private const val TOTAL_HOURS_HALF_DAY = 12
private const val AM = "AM"
private const val PM = "PM"

fun getHourInPmOrAmFormat(hourOfDay: Int, minute: Int): String =
    String.format(FORMAT_HOUR, getHour(hourOfDay), minute, getPeriodOfDay(hourOfDay))

private fun getPeriodOfDay(hourOfDay: Int): String {
    return if (hourOfDay >= TOTAL_HOURS_HALF_DAY) PM else AM
}

private fun getHour(hourOfDay: Int) =
    if (hourOfDay == TOTAL_HOURS_HALF_DAY || hourOfDay == 0) TOTAL_HOURS_HALF_DAY else hourOfDay % TOTAL_HOURS_HALF_DAY

fun dayOfTheWeek(): Int {
    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_WEEK)
    return day
}
