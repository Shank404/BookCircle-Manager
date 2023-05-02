package de.hsflensburg.model.commands.user

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateCommand (
    val username : String? = null,
    val password : String? = null,
    val role : String? = null
)
