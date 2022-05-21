package io.github.actar233.syntax.statement

import io.github.actar233.lexical.Token
import io.github.actar233.syntax.Context
import io.github.actar233.syntax.expr.Expression
import io.github.actar233.utils.RecordIterator

class WhileStatement(val iterator: RecordIterator<Token>) : Statement {

    override var canBreak: Boolean = false

    private val expr = RecordIterator<Token>()

    private val block = RecordIterator<Token>()

    init {
        if (iterator.current.type == Token.Type.KEYWORD_WHILE) {
            iterator.next
            val condition = RecordIterator<Token>()
            var paren = 1
            iterator.next
            while (true) {
                if (iterator.current.type == Token.Type.OPERATOR_CLOSE_PAREN) {
                    paren--
                    if (paren == 0) {
                        iterator.next
                        break
                    }
                }
                if (iterator.current.type == Token.Type.OPERATOR_OPEN_PAREN) {
                    paren++
                }
                condition += iterator.current
                iterator.next
            }
            expr += condition
            paren = 1
            iterator.next
            while (true) {
                if (iterator.current.type == Token.Type.SYMBOL_CLOSE_BRACE) {
                    paren--
                    if (paren == 0) {
                        break
                    }
                }
                if (iterator.current.type == Token.Type.SYMBOL_OPEN_BRACE) {
                    paren++
                }
                block += iterator.current
                iterator.next
            }
        } else {
            throw RuntimeException("出现错误")
        }
    }

    private fun tryNext(){
        if(iterator.hasNext){
            iterator.next
        }
    }

    override fun execute(context: Context) {
        while (Expression(expr.clone(),context).value().isTrue()){
            Block(block.clone()).execute(Context(context))
        }
    }

}