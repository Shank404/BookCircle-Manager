package de.hsflensburg.model.commands.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInsertCommand(
    val username: String,
    val password: String,
    val role: String
)
