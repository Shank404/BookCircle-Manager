package de.hsflensburg.server.plugins

import de.hsflensburg.model.commands.user.UserUpdateCommand
import de.hsflensburg.server.services.UserService
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import java.lang.IllegalArgumentException
fun Route.user() {

    val service by closestDI().instance<UserService>()

    get("/user/{uuid}") {
        val uuid = call.parameters["uuid"] ?: throw IllegalArgumentException("ID cannot be null")
        call.respond(service.getUserByUUID(uuid))
    }

    authenticate("jwt-auth") {
            route("/user") {
                get() {
                    call.respond(service.getAllUsers())
                }

                post("/{uuid}") {
                    val uuid = call.parameters["uuid"] ?: throw IllegalArgumentException("UUID cannot be null")
                    val body = call.receive<UserUpdateCommand>()
                    service.updateUserByUUID(uuid, body)
                }

                delete("/{uuid}") {
                    val uuid = call.parameters["uuid"] ?: throw IllegalArgumentException("ID cannot be null")
                    service.deleteUserByUUID(uuid)
                    call.respond(HttpStatusCode.NoContent)
                }
        }
    }
}
