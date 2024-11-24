package ch.heigvd.iict.daa.template.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ch.heigvd.iict.daa.template.MyApp
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.viewModel.MyViewModelFactory
import ch.heigvd.iict.daa.template.viewModel.ViewModelNote

class FragmentControles : Fragment() {


    private val myViewModel: ViewModelNote by activityViewModels {
        MyViewModelFactory((requireActivity().application as MyApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_controles, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteCounter: TextView = view.findViewById(R.id.notes_counter)
        val buttonCreate: Button = view.findViewById(R.id.button_generate)
        val buttonDelete: Button = view.findViewById(R.id.button_delete)

        myViewModel.countNote.observe(viewLifecycleOwner) { count ->
            noteCounter.text = "Notes Count: $count"
        }

        buttonCreate.setOnClickListener {
            myViewModel.generateNote()
        }

        buttonDelete.setOnClickListener {
            myViewModel.deleteNote()
        }
    }
}
