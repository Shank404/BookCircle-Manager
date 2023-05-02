package de.hsflensburg.server.persistence

import de.nycode.bcrypt.hash
import kotlinx.serialization.Serializable

@Serializable
data class UserInsertCommand(
    val username: String,
    val password: String,
    val role: String
)
fun UserInsertCommand.toEntity() = UserEntity.new {
    username = this@toEntity.username
    password = hash(this@toEntity.password, 10).decodeToString()
    role = this@toEntity.role
}