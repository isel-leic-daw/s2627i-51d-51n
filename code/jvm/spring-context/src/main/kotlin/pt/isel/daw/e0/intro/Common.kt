package pt.isel.daw.e0.intro

import java.security.MessageDigest
import java.util.function.Supplier

@JvmInline
value class UserId(
    val id: String,
)

@JvmInline
value class PlainTextPassword(
    val value: String,
)

@JvmInline
value class TransformedPassword(
    val value: String,
)

interface PasswordTransformer {
    fun transform(plainTextPassword: PlainTextPassword): TransformedPassword

    fun check(plainTextPassword: PlainTextPassword, transformedPassword: TransformedPassword): Boolean
}

interface PasswordStorage {
    fun set(userId: UserId, transformedPassword: TransformedPassword)

    fun get(userId: UserId): TransformedPassword?
}

fun interface HashFunctionSupplier : Supplier<MessageDigest>