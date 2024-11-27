/*
Auteur :  Shyshmarov Alexandre / Guilherme Pinto
Description : La classe ViewModelNote gère les données des notes en s'appuyant sur un Repository,
offre des fonctionnalités de tri et de gestion des notes avec support pour LiveData, tandis
que la MyViewModelFactory facilite la création personnalisée de cette classe.
 */

package ch.heigvd.iict.daa.template.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.repository.Repository


// Définition d'un ViewModel pour gérer les données liées aux notes et leur cycle de vie.
class ViewModelNote(private val repository: Repository) : ViewModel() {

    val allNote : LiveData<List<NoteAndSchedule>> = repository.getAllNote()
    val countNote : LiveData<Int> = repository.countNotes()


    private val _sortOrder = MutableLiveData(SortOrder.BY_CREATION_DATE)
    val sortOrder: LiveData<SortOrder> = _sortOrder

    private val _sortedNotes = MediatorLiveData<List<NoteAndSchedule>>()
    val sortedNotes: LiveData<List<NoteAndSchedule>> = _sortedNotes

    init {
        _sortedNotes.addSource(allNote) { updateSortedNotes() }
        _sortedNotes.addSource(sortOrder) { updateSortedNotes() }
    }


private fun updateSortedNotes() {
    val notes = allNote.value ?: return
    val sorted = when (_sortOrder.value) {
        SortOrder.BY_CREATION_DATE -> notes.sortedBy { it.note.creationDate.time } // Utilisation de .time
        SortOrder.BY_SCHEDULE_DATE -> notes.sortedWith(
            compareBy<NoteAndSchedule> { it.schedule?.date?.timeInMillis ?: Long.MAX_VALUE } // Utilisation de .time
        )
        null -> notes // Ajout d'un cas pour null
    }
    _sortedNotes.value = sorted
}


    fun setSortOrder(sortOrder: SortOrder) {
        _sortOrder.value = sortOrder
    }

    // Méthode pour trier les notes par date de création
    fun sortNotesByCreationDate() {
        setSortOrder(SortOrder.BY_CREATION_DATE)
    }

    // Méthode pour trier les notes par ETA (Schedule Date)
    fun sortNotesByETA() {
        setSortOrder(SortOrder.BY_SCHEDULE_DATE)
    }

    // Fonction pour supprimer toutes les notes.
    // Elle appelle une méthode correspondante dans le Repository.
    fun deleteNote() {
        repository.deletesNotes()
    }
    // Fonction pour générer une note aléatoire.
    // Le Repository gère cette opération.
    fun generateNote() {
        repository.generateRandNote()
    }

    enum class SortOrder {
        BY_CREATION_DATE, BY_SCHEDULE_DATE
    }
}

// Factory pour créer des instances de ViewModel personnalisées.
class MyViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    // Méthode de création qui instancie un ViewModel spécifique.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Vérifie si le ViewModel demandé est de type ViewModelNote.
        if (modelClass.isAssignableFrom(ViewModelNote::class.java)) {
            // Si oui, crée une instance de ViewModelNote en passant le Repository.
            return ViewModelNote(repository) as T
        }
        // Si le ViewModel demandé n'est pas reconnu, lève une exception.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}