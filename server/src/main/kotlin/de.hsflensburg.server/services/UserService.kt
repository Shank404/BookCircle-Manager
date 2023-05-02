package de.hsflensburg.server.services

import de.hsflensburg.model.User
import de.hsflensburg.model.commands.user.UserExistsCommand
import de.hsflensburg.model.commands.user.UserLoginCommand
import de.hsflensburg.model.commands.user.UserUpdateCommand
import de.hsflensburg.server.persistence.*
import de.nycode.bcrypt.verify
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserService {

    fun getAllUsers() : List<User> = transaction {
        UserEntity.all().map {it.toDTO()}
    }

    fun getUserByUUID(uuid : String) : User = transaction {
        val user = UserEntity.findById(UUID.fromString(uuid))
        return@transaction user!!.toDTO()
    }

    fun existsUserWithUsername(command : UserExistsCommand): Boolean = transaction {
        val existingUser = UserEntity.find { UsersTable.username eq command.username }.limit(1).firstOrNull()?.toDTO()
        var exists = false
        existingUser?.let {
            exists = true
        }
        exists
    }

    fun loginUser(command : UserLoginCommand): User? = transaction {
        val userExists = existsUserWithUsername(UserExistsCommand(command.username))
        var result : User? = null
        if (userExists) {
            val user = UserEntity.find { UsersTable.username eq command.username }.limit(1).firstOrNull()?.toDTO()
            if (user != null) {
                if (verify(command.password, user.password.toByteArray())) {
                    result = user
                }
            }
        }
        result
    }

    fun insertUser(command : UserInsertCommand) : Boolean = transaction {
        val existingUser = UserEntity.find { UsersTable.username eq command.username }.limit(1).firstOrNull()?.toDTO()
        var userExists = false
        if(existingUser?.username != command.username){
            userExists = false
            val user = command.toEntity()
            user.flush()
        } else {
            userExists = true
        }
        userExists
    }

    fun updateUserByUUID(uuid : String, command: UserUpdateCommand) = transaction {
        val existingUser = UserEntity.findById(UUID.fromString(uuid))
        if(existingUser != null){
            existingUser.username = command.username!!
            existingUser.password = command.password!!
            existingUser.role = command.role!!
        }
    }

    fun deleteUserByUUID(uuid: String) : User? = transaction {
        val user = UserEntity.findById(UUID.fromString(uuid))
        UserEntity.findById(UUID.fromString(uuid))?.delete()
        user?.toDTO()
    }
}