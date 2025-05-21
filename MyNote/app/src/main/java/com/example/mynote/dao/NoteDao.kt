package com.example.mynote.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mynote.models.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}