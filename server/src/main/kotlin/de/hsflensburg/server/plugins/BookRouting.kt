package de.hsflensburg.server.plugins

import de.hsflensburg.model.commands.book.BookUpdateCommand
import de.hsflensburg.server.services.BookService
import de.hsflensburg.server.persistence.BookInsertCommand
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import java.lang.IllegalArgumentException

fun Route.book() {

    val service by closestDI().instance<BookService>()


    get("/book/{uuid}") {
        val uuid = call.parameters["uuid"] ?: throw IllegalArgumentException("ID cannot be null")
        call.respond(service.getBookByUUID(uuid))
    }

    authenticate("jwt-auth") {
        route("/book") {
            get() {
                call.respond(service.getAllBooks())
            }

            get("/mybooks/{username}") {
                val username = call.parameters["username"] ?: throw IllegalArgumentException("ID cannot be null")
                call.respond(service.getAllBooksByUsername(username))
            }

            get("/lendedbooks/{username}") {
                val username = call.parameters["username"] ?: throw IllegalArgumentException("ID cannot be null")
                call.respond(service.getAllLendedBooksByUsername(username))
            }

            post() {
                val body = call.receive<BookInsertCommand>()
                val exists = service.insertBook(body)
                if (exists) {
                    call.respond(HttpStatusCode.Forbidden)
                } else {
                    call.respond(HttpStatusCode.Created)
                }
            }

            post("/{uuid}") {
                val uuid = call.parameters["uuid"] ?: throw IllegalArgumentException("UUID cannot be null")
                val body = call.receive<BookUpdateCommand>()
                service.updateBookByUUID(uuid, body)
            }
            delete("/{uuid}") {
                val uuid = call.parameters["uuid"] ?: throw IllegalArgumentException("ID cannot be null")
                service.deleteBookByUUID(uuid)
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}