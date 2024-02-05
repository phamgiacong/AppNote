package com.cong.noteapp.data.repository

import android.util.Log
import com.cong.noteapp.data.database.NoteDao
import com.cong.noteapp.data.model.NoteModel
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    fun getNotes(): List<NoteModel> {
        return try {
            noteDao.getNotes()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun insertNote(note: NoteModel) {
        try {
            noteDao.insertNote(note)
        } catch (e: Exception) {
            Log.e("Exception", "$e")
        }
    }

    fun updateNote(note: NoteModel) {
        try {
            noteDao.updateNote(note)
        } catch (e: Exception) {
            Log.e("Exception", "$e")
        }
    }

    fun deleteNote(note: NoteModel) {
        try {
            noteDao.deleteNote(note)
        } catch (e: Exception) {
            Log.e("Exception", "$e")
        }
    }

}