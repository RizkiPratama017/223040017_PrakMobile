package com.example.mynote

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynote.dao.NoteDao
import com.example.mynote.models.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
