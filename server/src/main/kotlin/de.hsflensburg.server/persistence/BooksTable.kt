package de.hsflensburg.server.persistence

import org.jetbrains.exposed.dao.id.UUIDTable

object BooksTable : UUIDTable() {
    val author = varchar("author", length=128)
    val title = varchar("title", length=128)
    val owner = varchar("owner", length=128)
    val lentTo = varchar("lentTo", length=128)
}