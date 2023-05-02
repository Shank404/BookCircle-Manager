package de.hsflensburg.common

import androidx.compose.runtime.*
import de.hsflensburg.model.Book
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.Color.black
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text


@Composable
actual fun MyBooks() {
    var listOfMyBooks = remember { mutableStateListOf<Book>() }

    val scope = rememberCoroutineScope()

    fun getMyBooks() {
        scope.launch {
            val listOfBooks = client.getAllBooksByUsername(username.value)
            listOfMyBooks.addAll(listOfBooks)
        }
    }

    fun deleteBook(book : Book) {
        scope.launch {
            client.deleteBook(book)
            listOfMyBooks.remove(book)
        }
    }

    getMyBooks()

    Div({
        style {
            display(DisplayStyle.Grid)
            gridTemplateRows(".2fr 1fr .2fr")
            gridTemplateAreas(". headline .", ". content .", ". buttons .")
            minHeight("100vh")
            justifyItems("center")
            alignItems("center")
        }
    }) {
        Div({
            style {
                gridArea("headline")
                fontSize(3.em)
                textAlign("center")
            }
        }) {
            H1 {
                Text("My books")
            }
        }

        Div({
            style {
                gridArea("content")
            }
        }) {
            for (book in listOfMyBooks) {
                Div({
                    style {
                        display(DisplayStyle.Flex)
                        flexDirection(FlexDirection.Column)
                        border(3.px, LineStyle.Solid, black)
                        borderRadius(3.em)
                        padding(2.em)
                    }
                }) {
                    Text("Author: " + book.author)
                    Text("Title: " + book.title)
                    Text("Lent to: " + book.lentTo)
                    Button(attrs = {
                        style {
                            fontSize(1.em)
                        }
                        onClick {
                            deleteBook(book)
                        }
                    }) {
                        Text("Delete")
                    }
                }
            }
        }

        Div({
            style {
                gridArea("buttons")
            }
        }) {
            Button(attrs = {
                style {
                    fontSize(2.em)
                    width(250.px)
                }
                onClick {
                    navigateOverview()
                }
            }) {
                Text("Back")
            }
        }
    }
}