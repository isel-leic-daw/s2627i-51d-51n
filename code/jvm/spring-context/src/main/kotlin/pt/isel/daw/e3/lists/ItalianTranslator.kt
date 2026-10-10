package pt.isel.daw.e3.lists

import org.springframework.stereotype.Component

@Component
class ItalianTranslator : LanguageTranslator {
    override fun translate(englishWord: String): String? {
        return if (englishWord.lowercase() == "hello") {
            "Ciao"
        } else {
            null
        }
    }

    override val targetLanguage: String = "it"
}