package pt.isel.daw.e0.intro.step0

import pt.isel.daw.e0.intro.PasswordStorage
import pt.isel.daw.e0.intro.PasswordTransformer
import pt.isel.daw.e0.intro.PlainTextPassword
import pt.isel.daw.e0.intro.TransformedPassword
import pt.isel.daw.e0.intro.UserId
import java.security.MessageDigest
import java.util.Base64
import java.util.concurrent.ConcurrentHashMap

/*
 * Step 0: initial state, with no inversion of control
 * - PasswordService is responsible for creating its dependencies
 */

class PasswordService {
    private val passwordTransformer: PasswordTransformer = InsecureSha1BasedPasswordTransformer()
    private val passwordStorage: PasswordStorage = InMemoryPasswordStorage()

    fun set(
        user: UserId,
        password: PlainTextPassword,
    ) {
        // Compute transformed password
        val transformedPassword = passwordTransformer.transform(password)

        // Store transformed password
        passwordStorage.set(user, transformedPassword)
    }

    fun check(
        user: UserId,
        password: PlainTextPassword,
    ): Boolean {
        // Retrieve stored transformed password
        val transformedPassword = passwordStorage.get(user) ?: return false

        // If present, check if they match
        return passwordTransformer.check(password, transformedPassword)
    }
}

class InsecureSha1BasedPasswordTransformer : PasswordTransformer {
    override fun transform(plainTextPassword: PlainTextPassword): TransformedPassword {
        val hashFunction: MessageDigest = MessageDigest.getInstance("SHA-256")
        val plainBytes: ByteArray = plainTextPassword.value.encodeToByteArray()
        val hashValue: ByteArray = hashFunction.digest(plainBytes)
        val hashValueAsString: String = Base64.getUrlEncoder().encodeToString(hashValue)
        return TransformedPassword(hashValueAsString)
    }

    override fun check(
        plainTextPassword: PlainTextPassword,
        transformedPassword: TransformedPassword,
    ): Boolean {
        val transformedHashValue: ByteArray = try {
            Base64.getUrlDecoder().decode(transformedPassword.value)
        } catch (_: IllegalArgumentException) {
            return false
        }
        val hashFunction: MessageDigest = MessageDigest.getInstance("SHA-256")
        val plainBytes: ByteArray = plainTextPassword.value.encodeToByteArray()
        val hashValue: ByteArray = hashFunction.digest(plainBytes)
        return hashValue.contentEquals(transformedHashValue)
    }
}

class InMemoryPasswordStorage : PasswordStorage {
    private val map = ConcurrentHashMap<String, String>()

    override fun set(userId: UserId, transformedPassword: TransformedPassword) {
        map[userId.id] = transformedPassword.value
    }

    override fun get(userId: UserId): TransformedPassword? {
        return map[userId.id]?.let { TransformedPassword(it) }
    }
}