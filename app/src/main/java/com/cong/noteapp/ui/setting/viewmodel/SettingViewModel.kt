package com.cong.noteapp.ui.setting.viewmodel

import android.app.Application
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.cong.noteapp.R
import com.cong.noteapp.data.model.LanguageModel

class SettingViewModel(application: Application) : AndroidViewModel(application) {
    val language = MutableLiveData<List<LanguageModel>>()

    init {
        val languages = listOf(
            LanguageModel("1", R.drawable.england, application.getString(R.string.en), "en"),
            LanguageModel("2", R.drawable.japan, application.getString(R.string.ja), "ja"),
            LanguageModel("3", R.drawable.laos, application.getString(R.string.lo), "lo"),
            LanguageModel("4", R.drawable.thailand, application.getString(R.string.th), "th"),
            LanguageModel("5", R.drawable.vietnam, application.getString(R.string.vi), "vi"),
            LanguageModel("6", R.drawable.china, application.getString(R.string.zh), "zh"),
        )
        language.postValue(languages)
    }
}