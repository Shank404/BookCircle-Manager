package de.hsflensburg.server

import de.hsflensburg.server.config.JwtConfig
import de.hsflensburg.server.persistence.BooksTable
import de.hsflensburg.server.persistence.UsersTable
import de.hsflensburg.server.plugins.configureRouting
import de.hsflensburg.server.services.BookService
import de.hsflensburg.server.services.UserService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.bindSingleton
import org.kodein.di.ktor.di

fun main() {
    Database.connect("jdbc:sqlite:books.db", driver = "org.sqlite.JDBC")

    transaction {
        SchemaUtils.create(BooksTable)
        SchemaUtils.create(UsersTable)
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHost("0.0.0.0:8081")
        allowHeader(HttpHeaders.ContentType)
        anyHost()
    }

    install(Authentication) {
        jwt("jwt-auth") {
            realm = "myRealm"
            verifier(JwtConfig.verifier)
            validate { credential ->
                if (credential.payload.getClaim("uuid").asString() != "" && credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }

    di {
        bindSingleton { BookService() }
        bindSingleton { UserService() }
    }

    configureRouting()
}