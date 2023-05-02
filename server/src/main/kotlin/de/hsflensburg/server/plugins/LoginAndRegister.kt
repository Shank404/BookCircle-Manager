package de.hsflensburg.server.plugins


import de.hsflensburg.model.commands.user.UserLoginCommand
import de.hsflensburg.server.config.JwtConfig
import de.hsflensburg.server.persistence.UserInsertCommand
import de.hsflensburg.server.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.loginAndRegister() {

    val service by closestDI().instance<UserService>()

    route("/login") {
        post {
            val body = call.receive<UserLoginCommand>()
            when (val user = service.loginUser(body)) {
                null -> call.respond(HttpStatusCode.Unauthorized)
                else -> call.respond(HttpStatusCode.Accepted, JwtConfig.makeToken(user)+"."+user.username+"."+user.id)
            }
        }
    }

    route("/user") {
        post() {
            val user = call.receive<UserInsertCommand>()
            val exists = service.insertUser(user)
            if (exists) {
                call.respond(HttpStatusCode.Forbidden)
            } else {
                call.respond(HttpStatusCode.Created)
            }
        }
    }
}
