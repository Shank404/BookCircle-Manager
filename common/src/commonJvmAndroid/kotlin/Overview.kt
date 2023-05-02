import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hsflensburg.common.*

@Composable
fun OverviewPage(){
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(35.dp))
        Text("Overview", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(100.dp))
        Button(onClick = ::navigateCreateBook, modifier = Modifier.fillMaxWidth()) {
            Text("Create a book")
        }
        Button(onClick = ::navigateMyBooks, modifier = Modifier.fillMaxWidth()) {
            Text("My books")
        }
        Button(onClick = ::navigateAllBooks, modifier = Modifier.fillMaxWidth()) {
            Text("All books")
        }
        Button(onClick = ::navigateLendedBooks, modifier = Modifier.fillMaxWidth()) {
            Text("Lended books")
        }
        Button(onClick = ::navigateLandingPage, modifier = Modifier.fillMaxWidth()) {
            Text("Logout")
        }

        if(userRole.value == "Admin"){
            Button(onClick = ::navigateAdmin, modifier = Modifier.fillMaxWidth()) {
                Text("Account Management for Admin-User")
            }
        }
    }
}