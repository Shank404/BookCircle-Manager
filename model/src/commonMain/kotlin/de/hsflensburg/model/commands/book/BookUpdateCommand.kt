package de.hsflensburg.model.commands.book

import kotlinx.serialization.Serializable

@Serializable
data class BookUpdateCommand (
    val author : String? = null,
    val title : String? = null,
    val owner : String? = null,
    val lentTo : String? = null
)
