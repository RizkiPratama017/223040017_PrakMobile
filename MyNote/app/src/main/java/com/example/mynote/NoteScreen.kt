package com.example.mynote

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benasher44.uuid.uuid4
import com.example.mynote.models.Note

@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val notes: List<Note> by viewModel.notesLiveData.observeAsState(emptyList())
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var editingId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TextField(
            value = title,
            onValueChange = { new -> title = new },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { new -> description = new },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (title.isBlank() || description.isBlank()) return@Button

                val note = Note(
                    id = editingId ?: uuid4().toString(),
                    title = title,
                    description = description
                )

                if (editingId == null) viewModel.insertNote(note)
                else {
                    viewModel.updateNote(note)
                    editingId = null
                }

                title = ""
                description = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (editingId == null) "Save Note" else "Update Note")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(items = notes) { note ->
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
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = note.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
