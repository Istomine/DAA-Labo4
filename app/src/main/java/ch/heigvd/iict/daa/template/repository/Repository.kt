package ch.heigvd.iict.daa.template.repository

import androidx.lifecycle.LiveData
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.dao.NoteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class Repository(private val noteDao: NoteDao, private val applicationScope: CoroutineScope) {

    fun getAllNote(): LiveData<List<NoteAndSchedule>> {
        return noteDao.getAllNotes()
    }

    fun deletesNotes(){
        applicationScope.launch {
            noteDao.deleteAllNotes()
        }
    }

    fun countNotes(): LiveData<Int>{
        return noteDao.countNotes()
    }

    fun countNotesLong(): Long{
        return noteDao.countNotesLong()
    }


    fun generateRandNote(){
        applicationScope.launch {
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