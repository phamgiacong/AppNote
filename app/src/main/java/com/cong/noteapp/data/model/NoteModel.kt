package com.cong.noteapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteModel(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var content: String,
    var date: String,
    var background: Int
)