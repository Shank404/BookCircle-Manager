import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hsflensburg.common.navigateLogin
import de.hsflensburg.common.navigateRegister

@Composable
fun LandingPagePage(){
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(35.dp))
        Text("Welcome to BookCircle!", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(200.dp))
        Button(onClick = ::navigateLogin, modifier = Modifier.requiredWidth(150.dp)){
            Text("Login")
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = ::navigateRegister, modifier = Modifier.requiredWidth(150.dp)){
            Text("Register")
        }
    }
}