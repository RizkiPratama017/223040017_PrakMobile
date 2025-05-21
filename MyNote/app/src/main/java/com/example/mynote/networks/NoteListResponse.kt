package com.example.mynote.networks

import com.example.mynote.models.Note

data class NoteListResponse(
    val message: String,
    val data:List<Note>
)
