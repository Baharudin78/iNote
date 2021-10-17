package com.baharudin.inote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.inote.R
import com.baharudin.inote.data.local.model.LocalNote
import com.baharudin.inote.databinding.ItemNoteBinding

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    var diffUtil = object : DiffUtil.ItemCallback<LocalNote>() {
        override fun areItemsTheSame(oldItem: LocalNote, newItem: LocalNote): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: LocalNote, newItem: LocalNote): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)
    var notes : List<LocalNote>
             get() = differ.currentList
             set(value) = differ.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = ItemNoteBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent, false
        )
        return NoteViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.apply {
            noteText.isVisible = note.noteTitle != null
            noteDescription.isVisible = note.description != null

            note.noteTitle?.let {
                noteText.text = it
            }
            note.description?.let {
                noteDescription.text = it
            }

            noteSync.setBackgroundResource(
                if (note.conected) R.drawable.synced
                else R.drawable.not_sync
            )

            root.setOnClickListener {
                onItemClickListener?.invoke(note)
            }
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
    private var onItemClickListener: ((LocalNote)-> Unit)? = null
    fun setOnItemClickListener(listener: (LocalNote)->Unit) {
        onItemClickListener = listener
    }
}