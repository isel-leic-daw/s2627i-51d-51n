package pt.isel.daw.springbootmvcintro

import org.springframework.stereotype.Component

@Component
class DefaultGreetingsService : GreetingsService {
    override fun getGreetings() = "Hello Web"
}
