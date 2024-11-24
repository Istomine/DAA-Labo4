package ch.heigvd.iict.daa.template

import FragmentNotes
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.iict.daa.template.fragments.FragmentControles
import ch.heigvd.iict.daa.template.viewModel.MyViewModelFactory
import ch.heigvd.iict.daa.template.viewModel.ViewModelNote
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val myViewModel: ViewModelNote by viewModels {
        MyViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutName = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            "res/layout/activity_main.xml"
        } else {
            "res/layout-land/activity_main.xml"
        }
        Log.d("MainActivity", "Layout chargé : $layoutName")

        Log.d("MainActivity", "Orientation détectée : ${resources.configuration.orientation}")

        val fragmentManager = supportFragmentManager

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("MainActivity", "Mode portrait : ajout du fragment Notes")
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentNotes())
                .commit()
        } else {
            Log.d("MainActivity", "Mode paysage : ajout des fragments Notes et Controles")
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_notes, FragmentNotes())
                .replace(R.id.fragment_controles, FragmentControles())
                .commit()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_notes, menu)
        return true
    }

    // Modifie dynamiquement les éléments du menu avant affichage (optionnel)
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            menu.findItem(R.id.action_generate)?.isVisible = false // Masque "Generate"
            menu.findItem(R.id.action_delete)?.isVisible = false // Masque "Delete All"
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