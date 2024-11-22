package ch.heigvd.iict.daa.template

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.iict.daa.labo4.models.*
import ch.heigvd.iict.daa.template.dao.NoteDao
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.thread


// Creation de la base de donnée
@Database(entities = [Note::class,Schedule::class],
    version = 1,
    exportSchema = true)
@TypeConverters(Converters::class)
abstract class DBnotes : RoomDatabase() {
    abstract fun NoteDao() : NoteDao


    companion object {
        private var INSTANCE : DBnotes? = null
        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context) : DBnotes {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    DBnotes::class.java, "mydatabase.db")
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .addCallback(DBnotesCallBack())
                    .build()
                INSTANCE!!
            }
        }


        private class DBnotesCallBack : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let {database ->
                    thread {
                        val isEmpty = database.NoteDao().countNotesLong() == 0L
                        if(isEmpty) {
                            //TODO FONCtion pour populer la DB avec val random
                        }
                    }
                }
            }
        }


        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE Person ADD COLUMN middleName INTEGER")
            }
        }
    }
}