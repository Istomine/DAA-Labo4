package ch.heigvd.iict.daa.template

import FragmentNotes
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager

            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FragmentNotes())
                    .commit()
            } else {
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_notes, FragmentNotes())
                    .replace(R.id.fragment_controles, FragmentControles())
                    .commit()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_notes, menu)
        return true
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