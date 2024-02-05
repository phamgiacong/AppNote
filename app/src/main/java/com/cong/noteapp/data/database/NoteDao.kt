package com.cong.noteapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cong.noteapp.data.model.NoteModel

@Dao
interface NoteDao {

    @Query("SELECT * FROM noteModel ORDER BY id DESC")
    fun getNotes(): List<NoteModel>

    @Insert
    fun insertNote(vararg note: NoteModel)

    @Update
    fun updateNote(note: NoteModel)

    @Delete
    fun deleteNote(note: NoteModel)

}