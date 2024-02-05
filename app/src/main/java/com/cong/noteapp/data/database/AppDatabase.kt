package com.cong.noteapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cong.noteapp.data.model.NoteModel

@Database(entities = [NoteModel::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}