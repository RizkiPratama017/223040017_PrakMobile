package com.example.mynote.networks

import com.example.mynote.models.Note

data class NoteSingleResponse(
    val message: String,
    val data: Note?
)
