package com.example.hellocompose
//Jetpack Compose latihan 1
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.example.hellocompose.ui.theme.Pink40
import com.example.hellocompose.ui.theme.Purple80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FormLogin(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun FormLogin(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val password = remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "Username", modifier = Modifier.padding(4.dp).fillMaxWidth())
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            modifier = Modifier.padding(4.dp).fillMaxWidth()
        )

        Text(text = "Password", modifier = Modifier.padding(4.dp).fillMaxWidth())
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.padding(4.dp).fillMaxWidth()
        )

        val loginButtonColors = ButtonDefaults.buttonColors(
            containerColor = Purple80,
            contentColor = Pink40
        )

        val resetButtonColors = ButtonDefaults.buttonColors(
            containerColor = Pink40,
            contentColor = Purple80
        )

        Spacer(modifier = Modifier.weight(1f).width(0.dp))

        Row(modifier = Modifier.padding(4.dp).fillMaxWidth()) {
            Button(
                modifier = Modifier.weight(5f), onClick = {
                    if(username.value.text == "admin" && password.value.text == "admin") {
                        Toast.makeText(context, "Login Sukses", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Login Gagal", Toast.LENGTH_LONG).show()
                    }
                }, colors = loginButtonColors
            ) {
                Text(
                    text = "Login",
                    style = TextStyle(color = Color.White, fontSize = 18.sp),
                    modifier = Modifier.padding(8.dp)
                )
            }

            Button(
                modifier = Modifier.weight(5f), onClick = {
                    username.value = TextFieldValue("")
                    password.value = TextFieldValue("")
                },
                colors = resetButtonColors
            ) {
                Text(
                    text = "Reset",
                    style = TextStyle(color = Color.White, fontSize = 18.sp),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloComposeTheme {
        FormLogin()
    }
}