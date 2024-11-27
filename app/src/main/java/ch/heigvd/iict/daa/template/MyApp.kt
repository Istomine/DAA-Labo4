/*
Auteur :  Shyshmarov Alexandre / Guilherme Pinto
Description : La classe MyApp étend Application pour fournir un cycle de vie global,
instancier la base de données et le repository de manière paresseuse, tout en configurant
un scope de coroutine pour les tâches en arrière-plan.
 */

package ch.heigvd.iict.daa.template

import android.app.Application
import ch.heigvd.iict.daa.template.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


// Surcharge de l'application pour pouvoir instancier la DB
class MyApp : Application() {

    // Déclaration d'un scope de coroutine associé au cycle de vie de l'application.
    private val applicationScope = CoroutineScope(SupervisorJob())
    // Initialisation paresseuse (lazy) du repository
    // L'instance est créée uniquement lorsque le repository est utilisé pour la première fois.
    val repository by lazy {
        // Obtention d'une instance de la base de données via un singleton
        val database = DBnotes.getDatabase(this)

        // Création du Repository en lui passant le DAO (Data Access Object)
        // et le scope de coroutine pour exécuter les opérations en arrière-plan.
        Repository(database.NoteDao(), applicationScope)
    }
}