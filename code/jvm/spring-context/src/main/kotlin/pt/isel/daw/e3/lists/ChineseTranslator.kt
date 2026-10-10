package pt.isel.daw.e3.lists

import org.springframework.stereotype.Component

@Component
class ChineseTranslator : LanguageTranslator {
    override fun translate(englishWord: String): String? {
        return if (englishWord.lowercase() == "hello") {
            "你好"
        } else {
            null
        }
    }

    override val targetLanguage: String = "zh-CN"
}