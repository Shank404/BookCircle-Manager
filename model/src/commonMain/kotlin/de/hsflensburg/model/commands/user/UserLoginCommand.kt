package de.hsflensburg.model.commands.user

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginCommand (
    val username : String,
    val password : String
)

