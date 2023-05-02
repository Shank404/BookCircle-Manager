import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hsflensburg.common.client
import de.hsflensburg.common.navigateLandingPage
import de.hsflensburg.model.User
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.launch

@Composable
fun RegisterPage() {
    var username = remember { mutableStateOf(TextFieldValue()) }
    var password = remember { mutableStateOf(TextFieldValue()) }
    var error = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    fun createUser(){
        scope.launch {
            val response : HttpResponse = client.createUser(User(username.value.text,password.value.text, "User"))
            if(response.status == HttpStatusCode.Created){
                username.value = TextFieldValue("")
                password.value = TextFieldValue("")
                error.value = "Erfolgreich registriert!"
            } else {
                error.value = "Existiert bereits!"
            }
        }
    }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(35.dp))
        Text("Register", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(200.dp))
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = "Username") }
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Text(error.value)
        Spacer(modifier = Modifier.height(150.dp))

        Button(onClick = ::createUser, modifier = Modifier.requiredWidth(120.dp)) {
            Text("Register")
        }
        Button(onClick = ::navigateLandingPage, modifier = Modifier.requiredWidth(120.dp)){
            Text("Back")
        }
    }
}