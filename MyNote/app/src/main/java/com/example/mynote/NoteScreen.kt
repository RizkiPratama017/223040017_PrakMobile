package com.example.mynote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benasher44.uuid.uuid4
import com.example.mynote.models.Note


@Composable
fun NoteScreen(modifier: Modifier) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val notes: List<Note> by viewModel.list.observeAsState(initial = listOf())

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var editingId by remember { mutableStateOf<String?>(null) }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (title.isBlank() || description.isBlank()) return@Button

                if (editingId == null) {
                    viewModel.insertNote(
                        Note(id = uuid4().toString(), title = title, description = description)
                    )
                } else {
                    // update existing
                    viewModel.updateNote(
                        Note(id = editingId!!, title = title, description = description)
                    )
                    editingId = null
                }
                title = ""
                description = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (editingId == null) "Save Note" else "Update Note")
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    onClick = {
                        editingId = note.id
                        title = note.title
                        description = note.description
                    }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(note.title, style = MaterialTheme.typography.titleMedium)
                        Text(note.description, style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
