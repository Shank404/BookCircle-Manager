package de.hsflensburg.common

import androidx.compose.runtime.*
import de.hsflensburg.model.Book
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
@Composable
actual fun AllBooks() {
    var list = remember { mutableStateOf(listOf<Book>()) }
    val scope = rememberCoroutineScope()
    var error = remember { mutableStateOf("") }

    fun getAllBooks() {
        scope.launch {
            val listOfBooks = client.getAllBooks()
            list.value = listOfBooks
        }
    }

    fun lendBook(username: String, book : Book){
            scope.launch {
                if(book.lentTo == "noone"){
                    client.lendBook(username, book.id)
                    error.value = book.title + " ausgeliehen"
                } else {
                    error.value = "Buch ist bereits verliehen"
                }
        }
    }

    getAllBooks()

    Div({
        style {
            display(DisplayStyle.Grid)
            gridTemplateColumns("1fr 1fr 1fr")
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
                Text("All Books")
            }
        }

        Div({
            style {
                gridArea("content")
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
            }
        }) {
            for (book in list.value) {
                Div({
                    style {
                        display(DisplayStyle.Flex)
                        flexDirection(FlexDirection.Column)
                        border(3.px, LineStyle.Solid, Color.black)
                        borderRadius(3.em)
                        padding(2.em)
                    }
                }) {
                    Text("Author: " + book.author)
                    Text("Title: " + book.title)
                    Text("Owner: " + book.owner)
                    Button(attrs = {
                        style {
                            fontSize(1.em)
                        }
                        onClick {
                            lendBook(username.value, book)
                        }
                    }) {
                        Text("Lend book")
                    }
                }
            }
            Text(error.value)
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