import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hsflensburg.common.*
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.launch

@Composable
fun LoginPage() {
    var usernameLogin = remember { mutableStateOf(TextFieldValue()) }
    var passwordLogin = remember { mutableStateOf(TextFieldValue()) }
    var error = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    fun login(){
        scope.launch {
            val response = client.login(usernameLogin.value.text, passwordLogin.value.text)
            if(response.status == HttpStatusCode.Accepted){
                val token : String = response.body()
                val splittedToken = token.split(".")
                val tokenGlued = splittedToken[0]+"."+splittedToken[1]+"."+splittedToken[2]
                jwt.value = tokenGlued
                username.value = splittedToken[3]
                userUUID.value = splittedToken[4]
                userRole.value = client.getUserByUUID(userUUID.value).role
                println(userRole.value)
                navigateOverview()
            } else {
                error.value = "Falsche Anmeldedaten, bitte versuchen Sie es erneut!"
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(35.dp))
        Text("Login", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(200.dp))
        TextField(
            value = usernameLogin.value,
            onValueChange = { usernameLogin.value = it },
            label = { Text(text = "Username") }
        )
        TextField(
            value = passwordLogin.value,
            onValueChange = { passwordLogin.value = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Text(error.value)
        Spacer(modifier = Modifier.height(150.dp))
        Button(onClick = ::login, modifier = Modifier.requiredWidth(120.dp)) {
            Text("Login")
        }
        Button(onClick = ::navigateLandingPage, modifier = Modifier.requiredWidth(120.dp)) {
            Text("Back")
        }
    }
}