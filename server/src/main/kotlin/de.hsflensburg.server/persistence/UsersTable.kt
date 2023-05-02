package de.hsflensburg.server.persistence

import org.jetbrains.exposed.dao.id.UUIDTable

object UsersTable : UUIDTable() {
    val username = varchar("username", length = 128)
    val password = varchar("password", length = 128)
    val role = varchar("role", length = 128)
}