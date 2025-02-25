package com.example.pertemuan_4

//pertemuan 4 latihan 2
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pertemuan_4.ui.theme.Pertemuan_4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pertemuan_4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FormRegistrasi(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun FormRegistrasi(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val nama = remember { mutableStateOf(TextFieldValue("")) }
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val nomorTelepon = remember { mutableStateOf(TextFieldValue("")) }
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val alamat = remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        TextFieldWithLabel("Nama", nama)
        TextFieldWithLabel("Username", username)
        TextFieldWithLabel("Nomor Telepon", nomorTelepon, KeyboardOptions(keyboardType = KeyboardType.Phone))
        TextFieldWithLabel("Email", email, KeyboardOptions(keyboardType = KeyboardType.Email))
        TextFieldWithLabel("Alamat", alamat)

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                if (nama.value.text.isNotBlank() && username.value.text.isNotBlank() &&
                    nomorTelepon.value.text.isNotBlank() && email.value.text.isNotBlank() &&
                    alamat.value.text.isNotBlank()) {
                    Toast.makeText(context, "Halo, ${nama.value.text}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Semua inputan harus diisi!", Toast.LENGTH_LONG).show()
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)) {
                Text(text = "Simpan", style = TextStyle(color = Color.White, fontSize = 18.sp))
            }

            Button(onClick = {
                nama.value = TextFieldValue("")
                username.value = TextFieldValue("")
                nomorTelepon.value = TextFieldValue("")
                email.value = TextFieldValue("")
                alamat.value = TextFieldValue("")
            }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text(text = "Reset", style = TextStyle(color = Color.White, fontSize = 18.sp))
            }
        }
    }
}

@Composable
fun TextFieldWithLabel(label: String, state: MutableState<TextFieldValue>, keyboardOptions: KeyboardOptions = KeyboardOptions.Default) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(text = label, modifier = Modifier.padding(4.dp))
        TextField(
            value = state.value,
            onValueChange = { state.value = it },
            keyboardOptions = keyboardOptions,
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFormRegistrasi() {
    Pertemuan_4Theme {
        FormRegistrasi()
    }
}