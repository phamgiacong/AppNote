package com.cong.noteapp.ui.setting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cong.noteapp.R
import com.cong.noteapp.data.model.LanguageModel
import com.cong.noteapp.databinding.ViewItemLanguageBinding
import com.cong.noteapp.ui.setting.viewholder.LanguageViewHolder

class LanguageAdapter(
    private val languages: List<LanguageModel>,
    private val onClick: OnClickItemLanguage,
    private val language: LanguageModel
) : RecyclerView.Adapter<LanguageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding =
            ViewItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val currentItem = languages[position]

        holder.layoutItemLanguage.setBackgroundResource(R.color.background_dialog)
        holder.imgLanguage.setImageResource(currentItem.image ?: 0)
        holder.tvLanguage.text = currentItem.name
        holder.imgChecked.visibility = View.GONE

        if (currentItem == language) {
            holder.layoutItemLanguage.setBackgroundResource(R.color.background_item_language)
            holder.imgChecked.visibility = View.VISIBLE
        }

        holder.layoutItemLanguage.setOnClickListener {
            onClick.setLanguage(currentItem)
        }
    }

    interface OnClickItemLanguage {
        fun setLanguage(language: LanguageModel)
    }
}