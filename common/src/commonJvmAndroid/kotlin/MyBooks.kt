import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hsflensburg.common.client
import de.hsflensburg.common.navigateOverview
import de.hsflensburg.common.username
import de.hsflensburg.model.Book
import kotlinx.coroutines.launch


@Composable
fun MyBooksPage() {
    var listOfMyBooks = remember { mutableStateListOf<Book>() }
    var scope = rememberCoroutineScope()
    scope.launch {
        client.getAllBooksByUsername(username.value).forEach{
            listOfMyBooks.add(it)
        }
    }
    fun deleteBook(book : Book){
        scope.launch {
            client.deleteBook(book)
            listOfMyBooks.remove(book)
        }
    }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("All Books from " + username.value, fontSize = 30.sp)
        Box(Modifier.weight(1F)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text("Title")
                Text("Author")
                Text("Owner")
            }
            MyBookList(
                book = listOfMyBooks,
                deleteBook = ::deleteBook
            )
        }
        Button(onClick = ::navigateOverview){
            Text("Zurück zur Overview")
        }
    }
}

@Composable
private fun MyBookList(
    book: List<Book>,
    deleteBook: (book : Book) -> Unit
) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(book) {
                Book(
                    book = it,
                    deleteBook = deleteBook
                )

                Divider()
            }
        }
    }
}
@Composable
private fun Book(
    book: Book,
    deleteBook: (book : Book) -> Unit
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

        Button(onClick = { deleteBook(book) }) {
            Text("Buch löschen")
        }
    }
}