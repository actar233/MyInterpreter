package io.github.actar233.syntax.expr

import io.github.actar233.exception.SyntaxException
import io.github.actar233.lexical.Token
import io.github.actar233.utils.RecordIterator
import io.github.actar233.syntax.Context
import io.github.actar233.utils.BoolValue
import io.github.actar233.utils.Value
import io.github.actar233.utils.at

class Expression(iterator: RecordIterator<Token>, val context: Context) : Item {
    private var value: Value? = null

    init {
        if (iterator.hasNext) {
            iterator.next
            if (iterator.current.type.at(Token.Type.KEYWORD_STRING, Token.Type.KEYWORD_NUMBER, Token.Type.KEYWORD_BOOL)) {
                var variableType = iterator.current.type
                iterator.next
                val variable = Variable(iterator, context)
                when (variableType) {
                    Token.Type.KEYWORD_STRING -> variable.create(Value.Type.STRING)
                    Token.Type.KEYWORD_NUMBER -> variable.create(Value.Type.NUMBER)
                    Token.Type.KEYWORD_BOOL -> variable.create(Value.Type.BOOL)
                    else -> {
                    throw RuntimeException("出现了错误")
                }
                }
            }
            value = generalCalculation(iterator)
        }
    }

    private fun generalCalculation(iterator: RecordIterator<Token>): Value {
        if (iterator.surplus == 0) {
            if (iterator.current.isID()) {
                return Variable(iterator, context).value()
            }
            if (iterator.current.isValue()) {
                return Value.Companion.parse(iterator.current)
            }
        }
        return if (iterator.current.isID() && iterator.watch(1).isOperator()) {
            if (iterator.watch(1).type == Token.Type.OPERATOR_ASSIGNMENT) {
                val variable = Variable(iterator, context)
                iterator.next
                iterator.next
                variable.value().assign(expr(iterator))
                variable.value()
            } else {
                expr(iterator)
            }
        } else {
            expr(iterator)
        }
    }

    private fun expr(iterator: RecordIterator<Token>):Value{
        return level12expr(iterator)
    }

    // 计算 括号
    private fun level1expr(iterator: RecordIterator<Token>): Value {
        if (iterator.hasNext && iterator.current.type == Token.Type.OPERATOR_OPEN_PAREN) {
            val sub = RecordIterator<Token>()
            var parens = 1
            while (iterator.hasNext) {
                iterator.next
                if (iterator.current.type == Token.Type.OPERATOR_CLOSE_PAREN) {
                    parens--
                    if (parens == 0) {
                        tryNext(iterator)
                        tryNext(sub)
                        return level10expr(sub)
                    }
                }
                if (iterator.current.type == Token.Type.OPERATOR_OPEN_PAREN) {
                    parens++
                }
                sub += iterator.current
            }
            throw RuntimeException("发生错误")
        }
        var result=getValue(iterator)
        if (iterator.hasNext && iterator.current.type == Token.Type.OPERATOR_DOT) {
            iterator.next
            if(iterator.current.isID()){
                result = result.findAttribute(iterator.current.value)
            }
        }
        return result
    }

    // 计算 负号 正号 逻辑非
    private fun level2expr(iterator: RecordIterator<Token>): Value {
        if (iterator.hasNext && iterator.current.type.at(Token.Type.OPERATOR_POSITIVE, Token.Type.OPERATOR_NEGATIVE, Token.Type.OPERATOR_NOT)) {
            if (iterator.current.type == Token.Type.OPERATOR_POSITIVE) {
                iterator.next
                return level1expr(iterator)
            } else if (iterator.current.type == Token.Type.OPERATOR_NEGATIVE) {
                iterator.next
                return Value.parse(Value.Type.NUMBER, "-1") * level1expr(iterator)
            } else if (iterator.current.type == Token.Type.OPERATOR_NOT) {
                iterator.next
                return !level1expr(iterator)
            }
            throw RuntimeException("发生错误")
        } else {
            return level1expr(iterator)
        }
    }

    // 计算 * /
    private fun level3expr(iterator: RecordIterator<Token>): Value {
        var result = level2expr(iterator)
        while (iterator.hasNext && iterator.current.type.at(Token.Type.OPERATOR_MULTIPLY, Token.Type.OPERATOR_DIVIDE)) {
            if (iterator.current.type == Token.Type.OPERATOR_MULTIPLY) {
                iterator.next
                result *= level2expr(iterator)
            } else if (iterator.current.type == Token.Type.OPERATOR_DIVIDE) {
                iterator.next
                result /= level2expr(iterator)
            }
        }
        return result
    }

    // 计算 + -
    private fun level4expr(iterator: RecordIterator<Token>): Value {
        var result = level3expr(iterator)
        while (iterator.hasNext && iterator.current.type.at(Token.Type.OPERATOR_PLUS, Token.Type.OPERATOR_MINUS)) {
            if (iterator.current.type == Token.Type.OPERATOR_PLUS) {
                iterator.next
                result += level3expr(iterator)
            } else if (iterator.current.type == Token.Type.OPERATOR_MINUS) {
                iterator.next
                result -= level3expr(iterator)
            }
        }
        return result
    }

    private fun level6expr(iterator: RecordIterator<Token>): Value {
        var result = level4expr(iterator)
        while (iterator.hasNext && iterator.current.type.at(Token.Type.OPERATOR_GREATER, Token.Type.OPERATOR_GREATER_EQUALS,
                        Token.Type.OPERATOR_LESS, Token.Type.OPERATOR_LESS_EQUALS)) {
            if (iterator.current.type == Token.Type.OPERATOR_GREATER) {
                iterator.next
                result = result greater level4expr(iterator)
            } else if (iterator.current.type == Token.Type.OPERATOR_GREATER_EQUALS) {
                iterator.next
                result = result greaterEquals level4expr(iterator)
            } else if (iterator.current.type == Token.Type.OPERATOR_LESS) {
                iterator.next
                result = result less level4expr(iterator)
            } else if (iterator.current.type == Token.Type.OPERATOR_LESS_EQUALS) {
                iterator.next
                result = result lessEquals level4expr(iterator)
            }
        }
        return result
    }

    private fun level7expr(iterator: RecordIterator<Token>): Value {
        var result = level6expr(iterator)
        while (iterator.hasNext && iterator.current.type.at(Token.Type.OPERATOR_EQUALS, Token.Type.OPERATOR_NOT_EQUALS)) {
            if (iterator.current.type == Token.Type.OPERATOR_EQUALS) {
                iterator.next
                result = result equals level6expr(iterator)
            } else if (iterator.current.type == Token.Type.OPERATOR_NOT_EQUALS) {
                iterator.next
                result = !(result equals level6expr(iterator))
            }
        }
        return result
    }

    private fun level8expr(iterator: RecordIterator<Token>): Value {
        var result = level7expr(iterator)
        if (iterator.hasNext && iterator.current.type == Token.Type.OPERATOR_AND) {
            iterator.next
            result = result and level7expr(iterator)
        }
        return result
    }

    private fun level9expr(iterator: RecordIterator<Token>): Value {
        var result = level8expr(iterator)
        if (iterator.hasNext && iterator.current.type == Token.Type.OPERATOR_XOR) {
            iterator.next
            result = result xor level8expr(iterator)
        }
        return result
    }

    private fun level10expr(iterator: RecordIterator<Token>): Value {
        var result = level9expr(iterator)
        if (iterator.hasNext && iterator.current.type == Token.Type.OPERATOR_OR) {
            iterator.next
            result = result or level9expr(iterator)
        }
        return result
    }

    private fun level11expr(iterator: RecordIterator<Token>): Value {
        var result = level10expr(iterator)
        if (iterator.hasNext && iterator.current.type == Token.Type.OPERATOR_LOGIC_AND) {
            if(result is BoolValue && (result.value() as Boolean)){
                iterator.next
                result = level10expr(iterator)
            }else{
                iterator.next
                skipValue(iterator)
            }
        }
        return result
    }

    private fun level12expr(iterator: RecordIterator<Token>): Value {
        var result = level11expr(iterator)
        if (iterator.hasNext && iterator.current.type == Token.Type.OPERATOR_LOGIC_OR) {
            if(result is BoolValue && !(result.value() as Boolean)){
                iterator.next
                result = level11expr(iterator)
            }else{
                iterator.next
                skipValue(iterator)
            }
        }
        return result
    }

    private fun skipValue(iterator: RecordIterator<Token>) {
        if(iterator.current.isValue()){
            return
        }
        if(iterator.current.isID()){
            return
        }
        if(iterator.current.isCall()){
            iterator.next
            var paren=0
            while (iterator.hasNext){
                if(iterator.current.type==Token.Type.OPERATOR_CLOSE_PAREN){
                    paren--
                    if(paren==0){
                        return
                    }
                }
                if(iterator.current.type==Token.Type.OPERATOR_OPEN_PAREN){
                    paren++
                }
                tryNext(iterator)
            }
            throw RuntimeException("出现错误")
        }
        if(iterator.current.type==Token.Type.OPERATOR_OPEN_PAREN){
            var paren=0
            while (iterator.hasNext){
                if(iterator.current.type==Token.Type.OPERATOR_CLOSE_PAREN){
                    paren--
                    if(paren==0){
                        return
                    }
                }
                if(iterator.current.type==Token.Type.OPERATOR_OPEN_PAREN){
                    paren++
                }
                tryNext(iterator)
            }
            throw RuntimeException("出现错误")
        }
        throw RuntimeException("无法跳过")
    }

    private fun getValue(iterator: RecordIterator<Token>): Value {
        if (iterator.current.isID()) {
            val value = context[iterator.current.value]
            tryNext(iterator)
            return value
        }
        if (iterator.current.isValue()) {
            val value = Value.parse(iterator.current)
            tryNext(iterator)
            return value
        }
        if (iterator.current.isCall()) {
            val value = CallFunction(iterator, context).value()
            tryNext(iterator)
            return value
        }
        throw SyntaxException("无法得到Value token=${iterator.current}")
    }

    private fun tryNext(iterator: RecordIterator<Token>) {
        if (iterator.hasNext) {
            iterator.next
        }
    }

    override fun value(): Value {
        return value!!
    }
}