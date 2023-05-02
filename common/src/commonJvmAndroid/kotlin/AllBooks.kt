import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hsflensburg.common.client
import de.hsflensburg.common.navigateOverview
import de.hsflensburg.common.username
import de.hsflensburg.model.Book
import kotlinx.coroutines.launch

var listOfBooks = mutableStateListOf<Book>()
@Composable
fun AllBooksPage() {
    listOfBooks.clear()
    val scope = rememberCoroutineScope()

    scope.launch {
        listOfBooks.addAll(client.getAllBooks())
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("All Books", fontSize = 30.sp)
        Box(Modifier.weight(1F)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text("Title")
                Text("Author")
            }
            BookList(
                book = listOfBooks
            )
        }
        Button(onClick = ::navigateOverview){
            Text("Zurück zur Overview")
        }
    }
}

@Composable
private fun BookList(
    book: List<Book>
) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(book) {
                Book(
                    book = it
                )
                Divider()
            }
        }
    }
}
@Composable
private fun Book(
    book: Book
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = AnnotatedString(book.title),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = AnnotatedString(book.author),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = AnnotatedString(book.owner),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))

        AlertDialogSampleBook(book)
    }
}

var alertDialogWidth = 400.dp
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AlertDialogSampleBook(book : Book) {
    Column(verticalArrangement = Arrangement.SpaceEvenly){
        val openDialog = remember { mutableStateOf(false)  }
        var scope = rememberCoroutineScope()
        Button(onClick = {
            openDialog.value = true
        }) {
            Text("Details")
        }

        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Box(modifier = Modifier.width(alertDialogWidth), contentAlignment = Alignment.Center){
                        Text(text = book.title, fontSize = 30.sp, fontStyle = FontStyle.Italic)
                    }
                },
                text = {
                    Column(modifier = Modifier.width(alertDialogWidth).height(200.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Author: " + book.author, fontSize = 20.sp)
                        Text("Owner: " + book.owner, fontSize = 20.sp)
                        Text("LentTo: " + book.lentTo, fontSize = 20.sp)
                    }

                },
                confirmButton = {
                    Box(modifier = Modifier.width(alertDialogWidth), contentAlignment = Alignment.Center){
                        Button(
                            onClick = {
                                openDialog.value = false
                                scope.launch {
                                    if(book.lentTo == "noone"){
                                        client.lendBook(username.value, book.id)
                                    }
                                }
                            }) {
                            if(book.lentTo == "noone"){
                                Text("Buch ausleihen?")
                            } else {
                                Text("Buch ist bereits verliehen")
                            }

                        }
                    }

                }
            )
        }
    }
}

