/*
Auteur :  Shyshmarov Alexandre / Guilherme Pinto
Description : L'interface NoteDao fournit des méthodes pour compter, insérer, supprimer
et récupérer des données depuis les tables note et schedule dans une base de données Room,
avec support pour LiveData.
 */

package ch.heigvd.iict.daa.template.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Schedule

@Dao
interface NoteDao {

    @Query("SELECT COUNT(*) FROM note")
    fun countNotes(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM note")
    fun countNotesLong(): Long


    // Insert note in the DB Return ID
    @Insert
    fun insert(note:Note): Long

    // Delete all notes
    @Query("DELETE FROM note")
    fun deleteAllNotes()

    // Get all notes
    @Query("SELECT * FROM note")
    fun getAllNotes(): LiveData<List<NoteAndSchedule>>

    // Insert a spesific schedul
    @Insert
    fun insert(schedule: Schedule)


}
