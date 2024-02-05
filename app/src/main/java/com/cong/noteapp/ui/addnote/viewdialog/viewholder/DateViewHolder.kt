package com.cong.noteapp.ui.addnote.viewdialog.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cong.noteapp.databinding.ViewItemDayBinding

class DateViewHolder(binding: ViewItemDayBinding) : RecyclerView.ViewHolder(binding.root) {
    var layoutContainer = binding.layoutContainer
    var layoutDay = binding.layoutDay
    var tvDay = binding.tvDay

    fun setEmptyContent() {
        itemView.visibility = View.INVISIBLE
    }
}