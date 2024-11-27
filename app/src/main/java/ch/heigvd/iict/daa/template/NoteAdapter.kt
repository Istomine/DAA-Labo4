/*
Auteur :  Shyshmarov Alexandre / Guilherme Pinto
Description : La classe NoteAdapter est un adaptateur pour un RecyclerView qui affiche
une liste de notes avec leurs détails et icônes associées, gère dynamiquement les mises
à jour via DiffUtil, et adapte l'affichage en fonction des données des notes et des plannings.
 */

package ch.heigvd.iict.daa.template

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import java.util.Calendar
import java.util.concurrent.TimeUnit

class NoteAdapter(
    private var items: List<NoteAndSchedule> = listOf()
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.list_item_title)
        val description: TextView = view.findViewById(R.id.list_item_description)
        val status: TextView? = view.findViewById(R.id.list_item_status)
        val statusIcon: ImageView? = view.findViewById(R.id.list_item_status_icon)
        val typeIcon: ImageView = view.findViewById(R.id.list_item_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_notes_item_clock, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = items[position]

        // Titre et description
        holder.title.text = note.note.title
        holder.description.text = note.note.text

        // Afficher le logo en fonction du Type
        holder.typeIcon.setImageResource(
            when (note.note.type) {
                ch.heigvd.iict.daa.labo4.models.Type.NONE -> R.drawable.note
                ch.heigvd.iict.daa.labo4.models.Type.TODO -> R.drawable.todo
                ch.heigvd.iict.daa.labo4.models.Type.SHOPPING -> R.drawable.shopping
                ch.heigvd.iict.daa.labo4.models.Type.WORK -> R.drawable.work
                ch.heigvd.iict.daa.labo4.models.Type.FAMILY -> R.drawable.family
            }
        )

        // Gestion du Schedule
        if (note.schedule != null) {
            holder.statusIcon?.visibility = View.VISIBLE // Afficher l'icône de la clock

            // Calcul du temps restant ou "Late"
            val currentTime = Calendar.getInstance()
            val scheduleTime = note.schedule.date
            val diffInMillis = scheduleTime.timeInMillis - currentTime.timeInMillis
            val diffInMonths = TimeUnit.MILLISECONDS.toDays(diffInMillis) / 30 // Approximation en mois

            holder.status?.text = if (diffInMillis < 0) {
                "Late" // Si la date est dépassée
            } else {
                "$diffInMonths months" // Temps restant
            }
            holder.status?.visibility = View.VISIBLE
        } else {
            holder.statusIcon?.visibility = View.GONE // Masquer l'icône de la clock
            holder.status?.visibility = View.GONE // Masquer le texte du status
        }
    }

    override fun getItemCount() = items.size

    fun updateNotes(newNotes: List<NoteAndSchedule>) {
        val diffCallback = NoteDiffCallback(items, newNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newNotes
        diffResult.dispatchUpdatesTo(this)
    }

    class NoteDiffCallback(
        private val oldList: List<NoteAndSchedule>,
        private val newList: List<NoteAndSchedule>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].note.noteId == newList[newItemPosition].note.noteId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
