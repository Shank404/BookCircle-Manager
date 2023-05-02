package de.hsflensburg.common

import AdminPage
import AllBooksPage
import CreateBookPage
import LandingPagePage
import LendedBooksPage
import LoginPage
import MyBooksPage
import OverviewPage
import RegisterPage
import androidx.compose.runtime.Composable

@Composable
actual fun Register(){
    RegisterPage()
}
@Composable
actual fun CreateBook(){
    CreateBookPage()
}
@Composable
actual fun LendedBooks() {
    LendedBooksPage()
}
@Composable
actual fun Login() {
    LoginPage()
}
@Composable
actual fun MyBooks() {
    MyBooksPage()
}
@Composable
actual fun Overview() {
    OverviewPage()
}
@Composable
actual fun Admin() {
    AdminPage()
}

@Composable
actual fun AllBooks() {
    AllBooksPage()
}

@Composable
actual fun LandingPage() {
    LandingPagePage()
}