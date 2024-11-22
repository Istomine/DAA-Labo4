package ch.heigvd.iict.daa.template.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.template.repository.Repository


// Définition d'un ViewModel pour gérer les données liées aux notes et leur cycle de vie.
class ViewModelNote(private val repository: Repository) : ViewModel() {

    val allNote : LiveData<List<NoteAndSchedule>> = repository.getAllNote()
    val countNote : LiveData<Int> = repository.countNotes()


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