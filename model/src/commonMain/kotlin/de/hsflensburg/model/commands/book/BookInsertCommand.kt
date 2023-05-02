package de.hsflensburg.model.commands.book

import kotlinx.serialization.Serializable

@Serializable
data class BookInsertCommand(
    val author: String,
    val title: String,
    val owner: String,
    val lentTo: String
)
