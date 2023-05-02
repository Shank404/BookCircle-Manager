import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hsflensburg.common.client
import de.hsflensburg.common.navigateOverview
import de.hsflensburg.common.username
import io.ktor.http.*
import kotlinx.coroutines.launch

@Composable
fun CreateBookPage(){

    val bookTitle = remember { mutableStateOf(TextFieldValue()) }
    var bookAuthor = remember { mutableStateOf(TextFieldValue()) }
    var error = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    fun createBook(){
        scope.launch {
            val response = client.createBook(
                de.hsflensburg.model.Book(
                    bookAuthor.value.text,
                    bookTitle.value.text,
                    username.value,
                    "noone"
                )
            )
            if(response.status == HttpStatusCode.Created){
                bookTitle.value = TextFieldValue("")
                bookAuthor.value = TextFieldValue("")
                error.value = "Erfolgreich erstellt!"
            } else {
                error.value = "Buch existiert bereits!"
            }
        }
    }
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(35.dp))
        Text("Create a book", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(100.dp))
        Text("Buch erstellen")
        TextField(
            value = bookTitle.value,
            onValueChange = { bookTitle.value = it },
            label = { Text(text = "Title") }
        )
        TextField(
            value = bookAuthor.value,
            onValueChange = { bookAuthor.value = it },
            label = { Text(text = "Author") }
        )
        Text(error.value)
        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = ::createBook) {
            Text("Buch erstellen")
        }
        Button(onClick = ::navigateOverview) {
            Text("Back Home")
        }
    }
}