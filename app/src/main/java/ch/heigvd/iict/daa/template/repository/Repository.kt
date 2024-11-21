package ch.heigvd.iict.daa.template.repository

import androidx.lifecycle.LiveData
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.dao.NoteDao
import kotlin.concurrent.thread

class Repository(private val noteDao: NoteDao) {

    fun getAllNote(): LiveData<List<NoteAndSchedule>> {
        return noteDao.getAllNotes()
    }

    fun deletesNotes(){
        thread {
            noteDao.deleteAllNotes()
        }
    }

    fun countNotes(): LiveData<Int>{
        return noteDao.countNotes()
    }


    fun generateRandNote(){
        thread {
            val note = Note.generateRandomNote()
            val schedule = Note.generateRandomSchedule()

            val id = noteDao.insert(note)

            if (schedule != null) {
                schedule.ownerId = id;
                noteDao.insert(schedule)
            }

        }
    }

}