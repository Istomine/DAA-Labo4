package ch.heigvd.iict.daa.template.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.repository.Repository

class ViewModelNote(private val repository: Repository) : ViewModel() {

    val allNote : LiveData<List<NoteAndSchedule>> = repository.getAllNote()
    val countNote : LiveData<Int> = repository.countNotes()


    fun deleteNote(){
        repository.deletesNotes()
    }

    fun generateNote(){
        repository.generateRandNote()
    }

}