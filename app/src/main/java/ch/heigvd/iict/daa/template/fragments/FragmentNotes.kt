import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.adapters.NoteAdapter
import ch.heigvd.iict.daa.template.viewModel.MyViewModelFactory
import ch.heigvd.iict.daa.template.viewModel.ViewModelNote
import ch.heigvd.iict.daa.template.MyApp

@Suppress("DEPRECATION")
class FragmentNotes : Fragment() {

    private val viewModel: ViewModelNote by activityViewModels {
        MyViewModelFactory((requireActivity().application as MyApp).repository)
    }
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        setHasOptionsMenu(true) // Activer le menu
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialiser RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_notes)
        adapter = NoteAdapter()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        // Observer les notes triées et mettre à jour l'Adapter
        viewModel.sortedNotes.observe(viewLifecycleOwner) { notes ->
            adapter.updateNotes(notes)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_notes, menu) // Charge le menu XML
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_creation_date -> {
                viewModel.sortNotesByCreationDate()
                true
            }
            R.id.sort_by_eta -> {
                viewModel.sortNotesByETA()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}