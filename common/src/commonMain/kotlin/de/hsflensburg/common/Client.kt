package de.hsflensburg.common

import de.hsflensburg.model.Book
import de.hsflensburg.model.User
import de.hsflensburg.model.commands.book.BookInsertCommand
import de.hsflensburg.model.commands.book.BookUpdateCommand
import de.hsflensburg.model.commands.user.UserInsertCommand
import de.hsflensburg.model.commands.user.UserLoginCommand

import de.hsflensburg.model.commands.user.UserUpdateCommand
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class Client {
    var ip: String = ""
    var desktopIp: String = "http://localhost:8080"
    var mobileIp: String = "http://10.0.2.2:8080"
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }

        if (getPlatformName() == "Desktop") {
            ip = desktopIp
        } else if (getPlatformName() == "Android") {
            ip = mobileIp
        } else if (getPlatformName() == "Web"){
            ip = desktopIp
        }
    }

    suspend fun createBook(book: Book): HttpResponse {
        return client.post("$ip/book") {
            header(HttpHeaders.Authorization, "Bearer ${jwt.value}")
            contentType(ContentType.Application.Json)
            setBody(BookInsertCommand(book.author, book.title, book.owner, book.lentTo))
        }
    }

    suspend fun login(username: String, password: String): HttpResponse {
        return client.post("$ip/login") {
            contentType(ContentType.Application.Json)
            setBody(UserLoginCommand(username, password))
        }
    }

    suspend fun createUser(user: User): HttpResponse {
        val user = UserInsertCommand(user.username, user.password, user.role)
        return client.post("$ip/user") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }

    suspend fun getAllBooks(): List<Book> {
        val response = client.get("$ip/book") {
            header(HttpHeaders.Authorization, "Bearer ${jwt.value}")
        }
        return response.body()
    }

    suspend fun getAllUsers(): List<User> {
        return client.get("$ip/user") { header(HttpHeaders.Authorization, "Bearer ${jwt.value}") }.body()
    }
    suspend fun getUserByUUID(uuid: String): User {
        return client.get("$ip/user/$uuid") { header(HttpHeaders.Authorization, "Bearer ${jwt.value}") }.body()
    }

    suspend fun lendBook(lender: String, uuid: String) {
        val existingBook: Book = client.get("$ip/book/$uuid").body()
        client.post("$ip/book/$uuid") {
            header(HttpHeaders.Authorization, "Bearer ${jwt.value}")
            contentType(ContentType.Application.Json)
            setBody(BookUpdateCommand(existingBook.author, existingBook.title, existingBook.owner, lender))
        }
    }

    suspend fun returnBook(uuid: String) {
        val existingBook: Book = client.get("$ip/book/$uuid").body()
        client.post("$ip/book/$uuid") {
            header(HttpHeaders.Authorization, "Bearer ${jwt.value}")
            contentType(ContentType.Application.Json)
            setBody(BookUpdateCommand(existingBook.author, existingBook.title, existingBook.owner, "noone"))
        }
    }

    suspend fun editUserRole(user: User, role: String) {
        val existingUser: User = client.get("$ip/user/" + user.id).body()
        client.post("$ip/user/" + user.id) {
            header(HttpHeaders.Authorization, "Bearer ${jwt.value}")
            contentType(ContentType.Application.Json)
            setBody(UserUpdateCommand(existingUser.username, existingUser.password, role))
        }
    }

    suspend fun deleteUser(user: User) {
        client.delete("$ip/user/" + user.id) { header(HttpHeaders.Authorization, "Bearer ${jwt.value}") }
    }

    suspend fun deleteBook(book: Book) {
        client.delete("$ip/book/" + book.id) { header(HttpHeaders.Authorization, "Bearer ${jwt.value}") }
    }

    suspend fun getAllBooksByUsername(username: String): List<Book> {
        return client.get("$ip/book/mybooks/$username") { header(HttpHeaders.Authorization, "Bearer ${jwt.value}") }.body()
    }

    suspend fun getAllLendedBooksByUsername(username: String): List<Book> {
        return client.get("$ip/book/lendedbooks/$username") { header(HttpHeaders.Authorization, "Bearer ${jwt.value}") }.body()
    }
}