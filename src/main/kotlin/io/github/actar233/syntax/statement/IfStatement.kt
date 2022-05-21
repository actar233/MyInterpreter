package io.github.actar233.syntax.statement

import io.github.actar233.lexical.Token
import io.github.actar233.syntax.Context
import io.github.actar233.syntax.expr.Expression
import io.github.actar233.utils.RecordIterator

class IfStatement(val iterator: RecordIterator<Token>) : Statement {
    override var canBreak: Boolean = false
    private val blocks = ArrayList<RecordIterator<Token>>()
    private val expr = ArrayList<RecordIterator<Token>?>()

    init {
        If()
        elseIf()
        Else()
        MopUp()
    }

    private fun If() {
        if (iterator.current.type == Token.Type.KEYWORD_IF) {
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
            val sub = RecordIterator<Token>()
            paren = 1
            iterator.next
            while (true) {
                if (iterator.current.type == Token.Type.SYMBOL_CLOSE_BRACE) {
                    paren--
                    if (paren == 0) {
                        tryNext()
                        break
                    }
                }
                if (iterator.current.type == Token.Type.SYMBOL_OPEN_BRACE) {
                    paren++
                }
                sub += iterator.current
                iterator.next
            }
            blocks += sub
        } else {
            throw RuntimeException("出现错误")
        }
    }

    private fun elseIf(){
        while (iterator.hasNext && iterator.current.type == Token.Type.KEYWORD_ELSE_IF) {
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
            val sub = RecordIterator<Token>()
            paren = 1
            iterator.next
            while (true) {
                if (iterator.current.type == Token.Type.SYMBOL_CLOSE_BRACE) {
                    paren--
                    if (paren == 0) {
                        tryNext()
                        break
                    }
                }
                if (iterator.current.type == Token.Type.SYMBOL_OPEN_BRACE) {
                    paren++
                }
                sub += iterator.current
                iterator.next
            }
            blocks += sub
        }
    }

    private fun Else(){

        if (iterator.current.type == Token.Type.KEYWORD_ELSE) {
            iterator.next
            expr.add(null)

            val sub = RecordIterator<Token>()
            var paren = 1
            iterator.next
            while (true) {
                if (iterator.current.type == Token.Type.SYMBOL_CLOSE_BRACE) {
                    paren--
                    if (paren == 0) {
                        tryNext()
                        break
                    }
                }
                if (iterator.current.type == Token.Type.SYMBOL_OPEN_BRACE) {
                    paren++
                }
                sub += iterator.current
                iterator.next
            }
            blocks += sub
        }
    }

    private fun MopUp(){
        if(iterator.current.type!=Token.Type.SYMBOL_CLOSE_BRACE){
            iterator.back(1)
        }
    }

    private fun tryNext(){
        if(iterator.hasNext){
            iterator.next
        }
    }

    override fun execute(context: Context) {
        for(i in 0 until expr.size){
            val ex = expr[i]
            if(ex == null || Expression(ex.clone(),context).value().isTrue()){
                Block(blocks[i].clone()).execute(Context(context))
                break
            }
        }
    }
}