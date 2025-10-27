package com.github.Zambrinn.KotlinAPI.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.yaml.snakeyaml.resolver.Resolver
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${application.security.jwt.secret-key}")
    private val secretKeyString: String,

    @Value("\${application.security.jwt.expiration}")
    private val jwtExpirationMs: Long
) {
    fun generateToken(userDetails: UserDetails): String {
        return generateToken(emptyMap(), userDetails)
    }

    fun generateToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetails
    ): String {
        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(getSigningKey())
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }

    fun extractUsername(token: String): String {
        return extractClaim(token) { claims -> claims.subject }
    }

    private fun isTokenExpired(token: String): Boolean {
        val expirationDate = extractClaim(token) { claims -> claims.expiration }
        return expirationDate.before(Date())
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKeyString)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}