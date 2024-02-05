package com.cong.noteapp.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cong.noteapp.R
import com.cong.noteapp.data.model.NoteModel
import com.cong.noteapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val note = MutableLiveData<List<NoteModel>>()

    private var colors: List<Int> = listOf(
        R.color.background_1,
        R.color.background_2,
        R.color.background_3,
        R.color.background_4,
        R.color.background_5
    )

    init {
        getNotes()
    }

    private fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            note.postValue(noteRepository.getNotes())
        }
    }

    fun insertNote(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
            getNotes()
        }
    }

    fun updateNote(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateNote(note)
            getNotes()
        }
    }

    fun deleteNote(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
            getNotes()
        }
    }

    fun randomColor(): Int {
        return colors.random()
    }
}