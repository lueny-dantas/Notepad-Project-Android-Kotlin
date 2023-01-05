package br.com.alura.ceep.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.databinding.NoteItemBinding
import br.com.alura.ceep.extensions.tryloadImage
import br.com.alura.ceep.model.Note

class ListNotesAdapter(
    private val context: Context,
    var whenClickItem: (note: Note) -> Unit = {},
    notes: List<Note> = emptyList()
) : RecyclerView.Adapter<ListNotesAdapter.ViewHolder>() {

    private val notes = notes.toMutableList()

    class ViewHolder(
        private val binding: NoteItemBinding,
        private val WhenClickItem: (note: Note) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var note: Note

        init {
            itemView.setOnClickListener {
                if (::note.isInitialized) {
                    WhenClickItem(note)
                }
            }
        }

        fun bind(note: Note) {
            this.note = note
            val imageNote = binding.notaImageItem
            imageNote.visibility =
                if (note.image.isNullOrBlank()) {
                    GONE
                } else {
                    imageNote.tryloadImage(note.image)
                    VISIBLE
                }
            binding.notaTitleItem.text = note.title
            binding.notaDescriptionItem.text = note.description
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            NoteItemBinding
                .inflate(
                    LayoutInflater.from(context)
                ),
            whenClickItem
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size

    fun update(notes: List<Note>) {
        notifyItemRangeRemoved(0, this.notes.size)
        this.notes.clear()
        this.notes.addAll(notes)
        notifyItemInserted(this.notes.size)
    }

}
