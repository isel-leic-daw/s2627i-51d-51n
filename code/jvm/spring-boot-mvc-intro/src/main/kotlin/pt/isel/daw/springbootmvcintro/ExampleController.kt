package pt.isel.daw.springbootmvcintro

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController(
    private val greetingsService: GreetingsService,
) {
    @GetMapping("/")
    fun handler() = greetingsService.getGreetings()
}
