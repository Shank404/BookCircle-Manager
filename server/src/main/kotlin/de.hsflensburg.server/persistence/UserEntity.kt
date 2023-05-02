package de.hsflensburg.server.persistence

import de.hsflensburg.model.User
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class UserEntity(id : EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UsersTable)
    var username by UsersTable.username
    var password by UsersTable.password
    var role by UsersTable.role
}

fun UserEntity.toDTO() : User {
    return User(
        id = id.toString(),
        username = username,
        password = password,
        role = role
    )
}