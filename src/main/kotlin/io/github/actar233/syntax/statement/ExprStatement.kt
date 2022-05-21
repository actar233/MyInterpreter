package io.github.actar233.syntax.statement

import io.github.actar233.lexical.Token
import io.github.actar233.syntax.Context
import io.github.actar233.syntax.expr.Expression
import io.github.actar233.utils.RecordIterator

class ExprStatement(val iterator: RecordIterator<Token>) : Statement {
    override var canBreak: Boolean = false
    private val list = RecordIterator<Token>()

    init {
        while (iterator.hasNext && iterator.current.type != Token.Type.SYMBOL_COLON) {
            list += iterator.current
            iterator.next
        }
    }

    override fun execute(context: Context) {
        Expression(list.clone(), context)
    }
}