package com.example.mynote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mynote.models.Note
import com.example.mynote.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repo: NoteRepository
) : ViewModel() {

    val notesLiveData: LiveData<List<Note>> = repo.loadItems(
        onSuccess = { Log.d("NoteViewModel", "Sync from server succeeded") },
        onError   = { Log.d("NoteViewModel", "Sync failed: $it") }
    )
        .onStart { /* bisa kirim loading flag di sini */ }
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(
            note,
            onSuccess = { Log.d("NoteViewModel","Insert local success") },
            onError   = { Log.d("NoteViewModel","Insert remote failed: $it") }
        )
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo.update(
            note,
            onSuccess = { Log.d("NoteViewModel","Update local success") },
            onError   = { Log.d("NoteViewModel","Update remote failed: $it") }
        )
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repo.delete(
            note,
            onSuccess = { Log.d("NoteViewModel","Delete local success") },
            onError   = { Log.d("NoteViewModel","Delete remote failed: $it") }
        )
    }
}
