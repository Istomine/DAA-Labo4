/*
Auteur :  Shyshmarov Alexandre / Guilherme Pinto
Description : La classe Converters définit des méthodes pour convertir des objets Calendar
en Long et inversement, permettant de stocker des données de type Calendar dans une base de données Room.
 */

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