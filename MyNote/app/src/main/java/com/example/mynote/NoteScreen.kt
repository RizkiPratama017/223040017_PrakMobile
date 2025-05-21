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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.benasher44.uuid.uuid4
import com.example.mynote.models.Note
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(modifier: Modifier) {
    val context = LocalContext.current
    val dao = NoteDatabase.getDatabase(context).noteDao()
    val notes: List<Note> by dao
        .getAllNotes()
        .observeAsState(initial = emptyList())

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

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
                if (title.isNotBlank() && description.isNotBlank()) {
                    scope.launch {
                        dao.insertNote(
                            Note(
                                id = uuid4().toString(),
                                title = title,
                                description = description
                            )
                        )
                        title = ""
                        description = ""
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Note")
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(notes) { noteItem ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    onClick = {
                        scope.launch { dao.deleteNote(noteItem) }
                    }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = noteItem.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = noteItem.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
