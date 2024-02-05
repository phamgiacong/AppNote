package com.cong.noteapp.ui.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.cong.noteapp.databinding.ViewItemNoteBinding

class HomeViewHolder(binding: ViewItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
    var tvTitle = binding.tvTitle
    var tvContent = binding.tvContent
    var tvDate = binding.tvDate
    var layoutItemNote = binding.layoutItemNote
}