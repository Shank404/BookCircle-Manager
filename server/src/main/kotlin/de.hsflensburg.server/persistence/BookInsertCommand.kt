package de.hsflensburg.server.persistence

import kotlinx.serialization.Serializable

@Serializable
data class BookInsertCommand(
    val author: String,
    val title: String,
    val owner: String,
    val lentTo: String
)

fun BookInsertCommand.toEntity() = BookEntity.new {
    author = this@toEntity.author
    title = this@toEntity.title
    owner = this@toEntity.owner
    lentTo = this@toEntity.lentTo
}
