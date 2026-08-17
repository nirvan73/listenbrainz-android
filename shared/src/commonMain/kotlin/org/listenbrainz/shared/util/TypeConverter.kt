package org.listenbrainz.shared.util

import kotlin.time.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json

object TypeConverter {

    private val json = Json { ignoreUnknownKeys = true }

    @androidx.room.TypeConverter
    fun nullableListToJSON(list: List<String>?): String = json.encodeToString(list)

    @androidx.room.TypeConverter
    fun nullableListFromJSON(listJSON: String): List<String>? {
        return try {
            json.decodeFromString(listJSON)
        } catch (e: Exception) {
            null
        }
    }

    private val dateFormatter = LocalDateTime.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
        char(',')
        char(' ')
        dayOfMonth(Padding.NONE)
        char(' ')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        amPmHour(Padding.NONE)
        char(':')
        minute(Padding.ZERO)
        char(' ')
        amPmMarker("AM","PM")
    }

    @androidx.room.TypeConverter
    fun stringFromDate(date: LocalDateTime): String {
        return date.format(dateFormatter)
    }

    @androidx.room.TypeConverter
    fun dateFromString(string: String): LocalDateTime? {
        if(string.isBlank()){
            return null
        }
        return try {
            LocalDateTime.parse(string,dateFormatter)
        } catch (e: Exception){
            null
        }
    }

    fun stringFromEpochSeconds(epochSeconds: Long, dateFormat: DateTimeFormat<LocalDateTime> = LocalDateTime.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        dayOfMonth(Padding.ZERO)
        char(',')
        char(' ')
        amPmHour(Padding.NONE)
        char(':')
        minute(Padding.ZERO)
        char(' ')
        amPmMarker("AM","PM")
    }): String {
        val instant = Instant.fromEpochSeconds(epochSeconds)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime.format(dateFormat)
    }

}