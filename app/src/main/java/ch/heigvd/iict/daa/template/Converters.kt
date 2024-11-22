package ch.heigvd.iict.daa.template

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun toCalendar(dateLong: Long) =
        Calendar.getInstance().apply {
            time = Date(dateLong)
        }
    @TypeConverter
    fun fromCalendar(date: Calendar) =
        date.time.time // Long
}