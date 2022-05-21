package io.github.actar233.lexical

import io.github.actar233.utils.RecordIterator
import io.github.actar233.utils.at

class Tokenizer(private val source: String) {

    private fun createTokenHandle(): TokenHandle {
        val handle = TokenHandle()
        handle.register("\\s*((0\\.)?[0-9]+)\\s*", { value, _, iterator ->
            iterator += Token(Token.Type.VALUE_NUMBER, value)
        }, null)
        handle.register("\\s*(true|false)\\s*", { value, _, iterator ->
            iterator += Token(Token.Type.VALUE_BOOL, value)
        })
        handle.register("(\")", { _, _, iterator ->
            val e = EscapeCharacterHandle(source, handle.position)
            handle.position = e.position
            iterator += Token(Token.Type.VALUE_STRING, e.value())
        })
        handle.register("\\s*(null)\\s*", { value, _, iterator ->
            iterator += Token(Token.Type.VALUE_NULL, value)
        })
        handle.register("\\s*(Number)\\s+", { value, _, iterator ->
            iterator += Token(Token.Type.KEYWORD_NUMBER, value)
        })
        handle.register("\\s*(String)\\s+", { value, _, iterator ->
            iterator += Token(Token.Type.KEYWORD_STRING, value)
        })
        handle.register("\\s*(Bool)\\s+", { value, _, iterator ->
            iterator += Token(Token.Type.KEYWORD_BOOL, value)
        })
        handle.register("\\b(if)\\b", { value, _, iterator ->
            iterator += Token(Token.Type.KEYWORD_IF, value)
        })
        handle.register("\\b(if)\\b", { value, _, iterator ->
            iterator += Token(Token.Type.KEYWORD_IF, value)
        })
        handle.register("\\b(else\\s+if)\\b", { value, _, iterator ->
            iterator += Token(Token.Type.KEYWORD_ELSE_IF, value)
        })
        handle.register("\\b(else)\\b", { value, _, iterator ->
            iterator += Token(Token.Type.KEYWORD_ELSE, value)
        })
        handle.register("\\b(while)\\b", { value, _, iterator ->
            iterator += Token(Token.Type.KEYWORD_WHILE, value)
        })
        handle.register("(\\.)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_DOT, value)
        })
        handle.register("(\\()", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_OPEN_PAREN, value)
        })
        handle.register("(\\))", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_CLOSE_PAREN, value)
        })
        handle.register("(\\{)", { value, _, iterator ->
            iterator += Token(Token.Type.SYMBOL_OPEN_BRACE, value)
        })
        handle.register("(\\})", { value, _, iterator ->
            iterator += Token(Token.Type.SYMBOL_CLOSE_BRACE, value)
        })
        handle.register("(>=)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_GREATER_EQUALS, value)
        })
        handle.register("(>)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_GREATER, value)
        })
        handle.register("(<=)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_LESS_EQUALS, value)
        })
        handle.register("(<)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_LESS, value)
        })
        handle.register("(==)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_EQUALS, value)
        })
        handle.register("(!=)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_NOT_EQUALS, value)
        })
        handle.register("(!)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_NOT, value)
        })
        handle.register("(&&)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_LOGIC_AND, value)
        })
        handle.register("(&)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_AND, value)
        })
        handle.register("(\\^)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_XOR, value)
        })
        handle.register("(\\|\\|)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_LOGIC_OR, value)
        })
        handle.register("(\\|)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_OR, value)
        })

        handle.register("(\\+)", { value, last, iterator ->
            iterator += if (last == null || last.type.at(
                    Token.Type.OPERATOR_PLUS, Token.Type.OPERATOR_MINUS,
                    Token.Type.OPERATOR_MULTIPLY, Token.Type.OPERATOR_DIVIDE,
                    Token.Type.OPERATOR_ASSIGNMENT, Token.Type.OPERATOR_OPEN_PAREN
                )
            ) {
                Token(Token.Type.OPERATOR_POSITIVE, value)
            } else {
                Token(Token.Type.OPERATOR_PLUS, value)
            }
        })
        handle.register("(-)", { value, last, iterator ->
            iterator += if (last == null || last.type.at(
                    Token.Type.OPERATOR_PLUS, Token.Type.OPERATOR_MINUS,
                    Token.Type.OPERATOR_MULTIPLY, Token.Type.OPERATOR_DIVIDE,
                    Token.Type.OPERATOR_ASSIGNMENT, Token.Type.OPERATOR_OPEN_PAREN
                )
            ) {
                Token(Token.Type.OPERATOR_NEGATIVE, value)
            } else {
                Token(Token.Type.OPERATOR_MINUS, value)
            }
        })
        handle.register("(\\*)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_MULTIPLY, value)
        })
        handle.register("(/)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_DIVIDE, value)
        })
        handle.register("(=)", { value, _, iterator ->
            iterator += Token(Token.Type.OPERATOR_ASSIGNMENT, value)
        })
        handle.register("([A-Za-z_][A-Za-z_0-9]*)\\(", { value, _, iterator ->
            iterator += Token(Token.Type.CALL, value)
            iterator += Token(Token.Type.OPERATOR_OPEN_PAREN, "(")
        })
        handle.register("\\b([A-Za-z_][A-Za-z_0-9]*)\\b", { value, _, iterator ->
            iterator += Token(Token.Type.ID, value)
        })
        handle.register("(;)", { value, _, iterator ->
            iterator += Token(Token.Type.SYMBOL_COLON, value)
        })
        handle.register("(,)", { value, _, iterator ->
            iterator += Token(Token.Type.SYMBOL_COMMA, value)
        })
        return handle
    }

    fun parse(): RecordIterator<Token> {
        return createTokenHandle().execute(source)
    }
}