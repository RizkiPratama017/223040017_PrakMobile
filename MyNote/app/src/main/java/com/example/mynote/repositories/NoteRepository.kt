package com.example.mynote.repositories

import androidx.annotation.WorkerThread
import com.example.mynote.dao.NoteDao
import com.example.mynote.models.Note
import com.example.mynote.networks.NoteApi
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val api: NoteApi,
    private val dao: NoteDao
) {
    @WorkerThread
    fun loadItems(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Note>> = flow {
        // 1) emit semua catatan lokal dulu
        val local = dao.getAllNotesList()
        emit(local)

        // 2) lalu coba sinkron dari server
        api.all()
            .suspendOnSuccess {
                // RECEIVER lambdas: 'this' adalah ApiResponse.Success<NoteListResponse>
                data.data?.let { remoteList ->
                    dao.insertNotes(remoteList)
                    emit(remoteList)
                    onSuccess()
                }
            }
            .suspendOnError {
                onError(message())
                emit(local)
            }
            .suspendOnException {
                onError(message())
                emit(local)
            }
    }.flowOn(Dispatchers.IO)

    suspend fun insert(
        note: Note,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // offline-first: simpan lokal dulu
        dao.insertNote(note)
        onSuccess()
        // kemudian sinkron ke server
        api.insert(note)
            .suspendOnSuccess { /* nothing */ }
            .suspendOnError   { onError(message()) }
            .suspendOnException { onError(message()) }
    }

    suspend fun update(
        note: Note,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        dao.updateNote(note)
        onSuccess()
        api.update(note.id, note)
            .suspendOnSuccess { /* nothing */ }
            .suspendOnError   { onError(message()) }
            .suspendOnException { onError(message()) }
    }

    suspend fun delete(
        note: Note,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        dao.deleteNote(note)
        onSuccess()
        api.delete(note.id)
            .suspendOnSuccess { /* nothing */ }
            .suspendOnError   { onError(message()) }
            .suspendOnException { onError(message()) }
    }
}
