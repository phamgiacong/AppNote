package com.cong.noteapp.ui.addnote.viewdialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cong.noteapp.R
import com.cong.noteapp.databinding.ViewItemDayBinding
import com.cong.noteapp.ui.addnote.viewdialog.viewholder.DateViewHolder

class DaysOfWeekAdapter(
    private val daysOfWeek: List<String>
) : RecyclerView.Adapter<DateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = ViewItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return daysOfWeek.size
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val currentItem = daysOfWeek[position]

        holder.tvDay.text = currentItem
    }
}