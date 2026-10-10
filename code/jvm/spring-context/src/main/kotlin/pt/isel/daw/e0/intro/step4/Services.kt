package pt.isel.daw.e0.intro.step4

import org.springframework.stereotype.Component
import pt.isel.daw.e0.intro.HashFunctionSupplier
import pt.isel.daw.e0.intro.PasswordStorage
import pt.isel.daw.e0.intro.PasswordTransformer
import pt.isel.daw.e0.intro.PlainTextPassword
import pt.isel.daw.e0.intro.TransformedPassword
import pt.isel.daw.e0.intro.UserId
import java.security.MessageDigest
import java.util.Base64
import java.util.concurrent.ConcurrentHashMap

/*
 * Step 2: Applying inversion of control to UnsecureSha1BasedPasswordTransformer,
 * associated to the creation of the HashFunctionSupplier concept.
 */

@Component
class PasswordService(
    private val passwordTransformer: PasswordTransformer,
    private val passwordStorage: PasswordStorage,
) {
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

@Component
class InsecureHashBasedPasswordTransformer(
    private val hashFunctionSupplier: HashFunctionSupplier,
) : PasswordTransformer {
    override fun transform(plainTextPassword: PlainTextPassword): TransformedPassword {
        val hashFunction: MessageDigest = hashFunctionSupplier.get()
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
        val hashFunction: MessageDigest = hashFunctionSupplier.get()
        val plainBytes: ByteArray = plainTextPassword.value.encodeToByteArray()
        val hashValue: ByteArray = hashFunction.digest(plainBytes)
        return hashValue.contentEquals(transformedHashValue)
    }
}

@Component
class InMemoryPasswordStorage : PasswordStorage {
    private val map = ConcurrentHashMap<String, String>()

    override fun set(userId: UserId, transformedPassword: TransformedPassword) {
        map[userId.id] = transformedPassword.value
    }

    override fun get(userId: UserId): TransformedPassword? {
        return map[userId.id]?.let { TransformedPassword(it) }
    }
}