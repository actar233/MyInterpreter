package io.github.actar233.syntax.expr

import io.github.actar233.exception.SyntaxException
import io.github.actar233.lexical.Token
import io.github.actar233.syntax.Context
import io.github.actar233.utils.RecordIterator
import io.github.actar233.utils.Value
import io.github.actar233.utils.VoidValue
import java.util.*

class Variable(val iterator: RecordIterator<Token>, val context: Context) : Item {
    var name: String

    init {
        if (iterator.current.type == Token.Type.ID) {
            name = iterator.current.value
        } else {
            throw SyntaxException("不存在 ID ")
        }
    }

    fun create(type:Value.Type) {
        if(context.containsKey(name)){
            throw SyntaxException("变量 $name 已存在")
        }
        context[name] = Value.parse(type,null)
    }

    override fun value(): Value {
        return context[name]
    }
}