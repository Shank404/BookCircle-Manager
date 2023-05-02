package de.hsflensburg.common

import androidx.compose.runtime.*
import de.hsflensburg.model.Book

val client = Client()
var state = mutableStateOf("LandingPage")
var jwt = mutableStateOf("")
var username = mutableStateOf("")
var userUUID = mutableStateOf("")
var userRole = mutableStateOf("")

@Composable
fun App() {
    when(state.value){
        "CreateBook" -> CreateBook()
        "AllBooks" -> AllBooks()
        "Admin" -> Admin()
        "LandingPage" -> LandingPage()
        "LendedBooks" -> LendedBooks()
        "Login" -> Login()
        "MyBooks" -> MyBooks()
        "Overview" -> Overview()
        "Register" -> Register()
        }
}

