package de.hsflensburg.common

import androidx.compose.runtime.*
import de.hsflensburg.model.Book
import io.ktor.http.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
actual fun CreateBook() {
    var user by remember { mutableStateOf(username.value) }
    val bookTitle = remember { mutableStateOf("") }
    var bookAuthor = remember { mutableStateOf("") }
    var error = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    fun createBook(){
        scope.launch {
            val response = client.createBook(
                Book(
                    bookAuthor.value,
                    bookTitle.value,
                    user,
                    "noone"
                )
            )
            if(response.status == HttpStatusCode.Created){
                bookTitle.value = ""
                bookAuthor.value = ""
                error.value = "Erfolgreich erstellt!"
            } else {
                error.value = "Buch existiert bereits!"
            }
        }
    }

    Div({
        style {
            display(DisplayStyle.Grid)
            gridTemplateRows("1fr 1.5fr 1fr")
            gridTemplateColumns("1fr 1fr 1fr")
            gridTemplateAreas( ". headline .", ". inputs .", ". buttons .")
            minHeight("100vh")
            justifyItems("center")
            alignItems("center")
        }
    }) {

        Div({
            style {
                gridArea("headline")
                textAlign("center")
                fontSize(3.em)
            }
        }) {
            H1 {
                Text("Create a book")
            }
        }

        Div(attrs = {
            style {
                gridArea("inputs")
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                rowGap(20.px)
                fontSize(2.em)
                textAlign("center")
            }
        }) {
            Text("Title")
            Input(
                type = InputType.Text,
                attrs = {
                    id("Title")
                    onInput {
                        bookTitle.value = it.value
                    }
                    style {
                        width(300.px)
                        height(45.px)
                    }
                    placeholder("Title")
                }
            )
            Text("Author")
            Input(
                type = InputType.Text,
                attrs = {
                    id("Author")
                    onInput {
                        bookAuthor.value = it.value
                    }
                    style {
                        width(300.px)
                        height(45.px)
                    }
                    placeholder("Author")
                }
            )
        }

        Div({
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                rowGap(20.px)
                gridArea("buttons")
            }
        }) {
            Text(error.value)
            Button(attrs = {
                style {
                    fontSize(2.em)
                    width(250.px)
                }
                onClick {
                    createBook()
                }
            }) {
                Text("Create book")
            }
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
