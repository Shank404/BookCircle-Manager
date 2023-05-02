package de.hsflensburg.model.commands.user

import kotlinx.serialization.Serializable

@Serializable
data class UserExistsCommand(
    val username: String
)

