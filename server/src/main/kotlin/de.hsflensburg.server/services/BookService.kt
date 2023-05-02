package de.hsflensburg.server.services

import de.hsflensburg.model.Book
import de.hsflensburg.model.commands.book.BookUpdateCommand
import de.hsflensburg.server.persistence.BookInsertCommand
import de.hsflensburg.server.persistence.toEntity
import de.hsflensburg.server.persistence.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class BookService {

    fun getAllBooks() : List<Book> = transaction {
        BookEntity.all().map {it.toDTO()}
    }

    fun getBookByUUID(uuid : String) : Book = transaction {
        val book = BookEntity.findById(UUID.fromString(uuid))
        return@transaction book!!.toDTO()
    }

    fun getAllBooksByUsername(username : String) : List<Book> = transaction {
        var listOfBooks = mutableListOf<Book>()
        BookEntity.all().map {
            if(it.owner == username){
                listOfBooks.add(it.toDTO())
            }
        }
        return@transaction listOfBooks
    }
    fun getAllLendedBooksByUsername(username : String) : List<Book> = transaction {
        var listOfBooks = mutableListOf<Book>()
        BookEntity.all().map {
            if(it.lentTo == username){
                listOfBooks.add(it.toDTO())
            }
        }
        return@transaction listOfBooks
    }

    fun insertBook(command : BookInsertCommand) : Boolean = transaction {
        val existingBook = BookEntity.find { BooksTable.title eq command.title }.limit(1).firstOrNull()?.toDTO()
        var bookExists = false
        if(existingBook?.title != command.title){
            bookExists = false
            val book = command.toEntity()
            book.flush()
        } else {
            bookExists = true
        }
        bookExists
    }

    fun updateBookByUUID(uuid : String, command: BookUpdateCommand) = transaction {
        val existingBook = BookEntity.findById(UUID.fromString(uuid))
        if(existingBook != null){
            existingBook.author = command.author!!
            existingBook.title = command.title!!
            existingBook.owner = command.owner!!
            existingBook.lentTo = command.lentTo!!
        }
    }

    fun deleteBookByUUID(uuid: String) : Book? = transaction {
        val book = BookEntity.findById(UUID.fromString(uuid))
        BookEntity.findById(UUID.fromString(uuid))?.delete()
        book?.toDTO()
    }
}