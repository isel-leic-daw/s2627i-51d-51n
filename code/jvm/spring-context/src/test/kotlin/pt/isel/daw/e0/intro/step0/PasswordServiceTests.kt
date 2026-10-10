package pt.isel.daw.e0.intro.step0

import pt.isel.daw.e0.intro.PlainTextPassword
import pt.isel.daw.e0.intro.UserId
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PasswordServiceTests {
    @Test
    fun `basic test`() {
        // given: a password service
        val passwordService = PasswordService()

        // and: a set of test data
        val alice = UserId("Alice")
        val bob = UserId("Bob")
        val carol = UserId("Carol")
        val alicePassword = PlainTextPassword("Alice's password")
        val bobPassword = PlainTextPassword("Bob's password")

        // when: setting Alice and Bob passwords
        passwordService.set(alice, alicePassword)
        passwordService.set(bob, bobPassword)

        // then: password verification works as expected
        assertTrue(passwordService.check(alice, alicePassword))
        assertTrue(passwordService.check(bob, bobPassword))

        assertFalse(passwordService.check(alice, bobPassword))
        assertFalse(passwordService.check(bob, alicePassword))

        assertFalse(passwordService.check(carol, alicePassword))
        assertFalse(passwordService.check(carol, bobPassword))
    }
}