package com.cong.noteapp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cong.noteapp.data.model.NoteModel
import com.cong.noteapp.databinding.ViewItemNoteBinding
import com.cong.noteapp.ui.home.viewholder.HomeViewHolder

class HomeAdapter(
    private val notes: List<NoteModel>,
    private val context: Context,
    private val onClick: OnClick
) : RecyclerView.Adapter<HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ViewItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = notes[position]

        holder.tvTitle.text = currentItem.title
        holder.tvContent.text = currentItem.content
        holder.tvDate.text = currentItem.date

        holder.layoutItemNote.background.setTint(
            ContextCompat.getColor(
                context,
                currentItem.background ?: 0
            )
        )

        holder.layoutItemNote.setOnClickListener {
            onClick.edit(currentItem)
        }
    }

    interface OnClick {
        fun edit(note: NoteModel)
    }
}