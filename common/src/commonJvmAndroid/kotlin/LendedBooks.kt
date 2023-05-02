import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
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
fun LendedBooksPage() {
    var listOfLendedBooks = mutableStateListOf<Book>()
    var scope = rememberCoroutineScope()

    fun fillLendedBookList(){
        scope.launch {
            client.getAllLendedBooksByUsername(username.value).forEach {
                listOfLendedBooks.add(it)
            }
        }
    }
    fillLendedBookList()

    fun returnBook(uuid: String) {
        scope.launch {
            client.returnBook(uuid)
            listOfLendedBooks.clear()
            fillLendedBookList()
        }
    }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Lended Books from " + username.value, fontSize = 30.sp)
        Box(Modifier.weight(1F)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text("Title")
                Text("Author")
                Text("Lent_to")
                Text("Button")
            }
            LendedBookList(
                book = listOfLendedBooks,
                returnBook = ::returnBook
            )
        }
        Button(onClick = ::navigateOverview) {
            Text("Zurück zur Overview")
        }
    }
}

@Composable
private fun LendedBookList(
    book: List<Book>,
    returnBook: (id: String) -> Unit
) {
    Box {
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            items(book) {
                Book(
                    book = it,
                    returnBook = returnBook
                )
                Divider()
            }
        }
    }
}

@Composable
private fun Book(
    book: Book,
    returnBook: (id: String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
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
            text = AnnotatedString(book.lentTo),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { returnBook(book.id) }) {
            Text("Buch zurückgeben")
        }
    }
}
