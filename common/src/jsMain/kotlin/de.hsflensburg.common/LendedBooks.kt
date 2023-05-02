package de.hsflensburg.common

import androidx.compose.runtime.*
import de.hsflensburg.model.Book
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@Composable
actual fun LendedBooks(){

    var listOfLendedBooks = mutableStateListOf<Book>()
    val scope = rememberCoroutineScope()

    fun getLentBooks() {
        scope.launch {
            val listOfBooks = client.getAllLendedBooksByUsername(username.value)
            listOfLendedBooks.addAll(listOfBooks)
        }
    }

    fun returnLentBook(book : Book){
        scope.launch {
            client.returnBook(book.id)
            listOfLendedBooks.remove(book)
        }
    }

    getLentBooks()


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
                Text("Lended Books")
            }
        }

        Div({
            style {
                gridArea("content")
            }
        }) {
            for (book in listOfLendedBooks) {
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
                    Text("Ownder: " + book.owner)
                    Button(attrs = {
                        style {
                            fontSize(1.em)
                        }
                        onClick {
                            returnLentBook(book)
                        }
                    }) {
                        Text("Give back")
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