package io.github.actar233.lexical

import io.github.actar233.utils.RecordIterator

class Lexer(source: String) {

    private val tokenizer: Tokenizer = Tokenizer(source)

    private var list: RecordIterator<Token>? = null

    fun parse(): RecordIterator<Token> {
        if (list == null) {
            list = tokenizer.parse()
        }
        return list!!
    }
}