package io.github.actar233.syntax.statement

import io.github.actar233.lexical.Token
import io.github.actar233.syntax.Context
import io.github.actar233.utils.RecordIterator

class Block(val iterator: RecordIterator<Token>) : Statement {
    override var canBreak: Boolean = false
    private val statements = RecordIterator<Statement>()

    init {
        if (iterator.hasNext) {
            while (iterator.hasNext) {
                statements += when (iterator.next.type) {
                    Token.Type.KEYWORD_IF -> {
                        IfStatement(iterator)
                    }
                    Token.Type.KEYWORD_WHILE -> {
                        WhileStatement(iterator)
                    }
                    else -> {
                        ExprStatement(iterator)
                    }
                }
            }
        }
    }

    override fun execute(context: Context) {
        while (statements.hasNext) {
            statements.next.execute(context)
        }
    }
}