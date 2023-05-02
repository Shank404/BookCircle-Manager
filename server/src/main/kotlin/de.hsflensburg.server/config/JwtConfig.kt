package de.hsflensburg.server.config

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import de.hsflensburg.model.User
import java.util.*

object JwtConfig {

    private const val secret = "GbV8V7H6LZzutlqmhjEB"
    private const val issuer = "MASTER"
    private const val realm = "myRealm"
    private const val audience = "myAudience"
    private const val validityInMs = 36_000_00 * 10
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun makeToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("uuid", user.id)
        .withClaim("username", user.username)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}