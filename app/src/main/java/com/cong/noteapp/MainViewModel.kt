package com.cong.noteapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cong.noteapp.data.model.LanguageModel
import com.cong.noteapp.data.model.NoteModel
import dagger.hilt.android.lifecycle.HiltViewModel

class MainViewModel : ViewModel() {
    val sendNote = MutableLiveData<NoteModel>()
    var checkedDarkMode = MutableLiveData<Boolean>()
    var language = MutableLiveData<LanguageModel>()
}