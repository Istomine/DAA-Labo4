package ch.heigvd.iict.daa.template

import FragmentNotes
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.iict.daa.template.fragments.FragmentControles
import ch.heigvd.iict.daa.template.viewModel.MyViewModelFactory
import ch.heigvd.iict.daa.template.viewModel.ViewModelNote

class MainActivity : AppCompatActivity() {

    private val myViewModel: ViewModelNote by viewModels {
        MyViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager

        // Charger les fragments en fonction du layout sélectionné automatiquement
        if (findViewById<View>(R.id.fragment_notes) != null) {
            // Mode tablette
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_notes, FragmentNotes())
                .replace(R.id.fragment_controles, FragmentControles())
                .commit()
        } else {
            // Mode smartphone
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentNotes())
                .commit()
        }


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_notes, menu)
        return true
    }

    // Modifie dynamiquement les éléments du menu avant affichage (optionnel)
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {

        if (findViewById<View>(R.id.fragment_notes) != null) {
            menu.findItem(R.id.action_generate)?.isVisible = false
            menu.findItem(R.id.action_delete)?.isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_creation_date -> {
                myViewModel.sortNotesByCreationDate()
                true
            }
            R.id.sort_by_eta -> {
                myViewModel.sortNotesByETA()
                true
            }
            R.id.action_delete -> {
                myViewModel.deleteNote()
                true
            }
            R.id.action_generate -> {
                myViewModel.generateNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}