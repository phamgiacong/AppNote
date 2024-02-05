package com.cong.noteapp.ui.addnote.viewdialog.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.cong.noteapp.R
import com.cong.noteapp.databinding.ViewItemDayBinding
import com.cong.noteapp.ui.addnote.viewdialog.viewholder.DateViewHolder
import com.cong.noteapp.ui.addnote.viewmodel.DateViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class DaysInMonthAdapter(
    private val daysInMonth: List<String>,
    private val onClick: OnClick,
    private val context: Context,
    private val space: Int,
    private val year: Int,
    private val month: Int,
    private val dateViewModel: DateViewModel,
    private val fragment: Fragment
) : RecyclerView.Adapter<DateViewHolder>() {

    private val today = LocalDate.now()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = ViewItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return daysInMonth.size
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val currentItem = daysInMonth[position]

        bindView(holder, position, currentItem)
        setOnClickListener(holder, currentItem)
        observeData(holder, currentItem)

    }

    private fun bindView(holder: DateViewHolder, position: Int, currentItem: String) {

        if (position in -1..<space) {
            holder.setEmptyContent()
        }

        holder.tvDay.text = currentItem

    }

    private fun setOnClickListener(holder: DateViewHolder, currentItem: String) {

        holder.layoutContainer.setOnClickListener {
            onClick.selectDayInMonth(currentItem)
        }

    }

    private fun observeData(holder: DateViewHolder, currentItem: String) {
        dateViewModel.day.observe(fragment) { day ->

            if (currentItem == day && dateViewModel.year == year && dateViewModel.month == month) {
                holder.layoutDay.background.setTint(
                    ContextCompat.getColor(
                        context,
                        R.color.secondary_layout_item_dialog
                    )
                )
                holder.tvDay.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.tertiary_text
                    )
                )
                return@observe
            }

            resetView(holder)

            if (day != today.dayOfMonth.toString()
                && currentItem == today.dayOfMonth.toString()
                && year == today.year
                && month == today.monthValue
            ) {
                holder.tvDay.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.quaternary_text
                    )
                )
            }
        }
    }

    private fun resetView(holder: DateViewHolder) {
        holder.layoutDay.background.setTint(
            ContextCompat.getColor(
                context,
                R.color.primary_layout_item_dialog
            )
        )
        holder.tvDay.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.primary_text
            )
        )
    }

    interface OnClick {
        fun selectDayInMonth(date: String)
    }
}