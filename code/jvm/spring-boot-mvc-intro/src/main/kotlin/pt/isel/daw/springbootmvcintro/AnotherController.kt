package pt.isel.daw.springbootmvcintro

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AnotherController {
    @GetMapping("/another")
    fun handler() = "I'm here too"
}
