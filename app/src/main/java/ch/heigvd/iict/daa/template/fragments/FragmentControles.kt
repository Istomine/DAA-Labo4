package ch.heigvd.iict.daa.template.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.viewModel.ViewModelNote

class FragmentControles : Fragment() {

    private val viewModel: ViewModelNote by activityViewModels {
//        ViewModelNote.Factory(requireActivity().application)
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

        viewModel.countNote.observe(viewLifecycleOwner) { count ->
            noteCounter.text = "Notes Count: $count"
        }

        buttonCreate.setOnClickListener {
            // Generate a random note (implementation can be added here)
            viewModel.generateNote()
        }

        buttonDelete.setOnClickListener {
            viewModel.deleteNote()
        }
    }
}
