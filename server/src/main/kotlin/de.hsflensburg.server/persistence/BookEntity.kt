package de.hsflensburg.server.persistence

import de.hsflensburg.model.Book
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class BookEntity(id : EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<BookEntity>(BooksTable)
    var author by BooksTable.author
    var title by BooksTable.title
    var owner by BooksTable.owner
    var lentTo by BooksTable.lentTo
}

fun BookEntity.toDTO() : Book {
    return Book(
        id = id.toString(),
        author = author,
        title = title,
        owner = owner,
        lentTo = lentTo
    )
}