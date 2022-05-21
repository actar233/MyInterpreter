package io.github.actar233.syntax.expr

import io.github.actar233.exception.SyntaxException
import io.github.actar233.lexical.Token
import io.github.actar233.utils.RecordIterator
import io.github.actar233.syntax.Context
import io.github.actar233.utils.Value

class CallFunction(iterator: RecordIterator<Token>, val context: Context):Item {
    private var args: RecordIterator<Value>
    private var functionName: String
    init {
        args = RecordIterator()

        if (iterator.current.type == Token.Type.CALL) {
            functionName = iterator.current.value
            if(iterator.hasNext){
                iterator.next
                if (iterator.current.type == Token.Type.OPERATOR_OPEN_PAREN) {
                    iterator.next

                    val sub = RecordIterator<Token>()

                    var parens=1;

                    while (true) {
                        if(iterator.current.type==Token.Type.OPERATOR_CLOSE_PAREN){
                            parens--
                            if(parens==0){
                                if(sub.size>0){
                                    args += Expression(sub, context).value()
                                }
                                break
                            }
                        }

                        if (iterator.current.type==Token.Type.SYMBOL_COMMA){
                            if(sub.size>0){
                                args += Expression(sub, context).value()
                                iterator.next
                                sub.clear()
                            }
                            continue
                        }
                        if(iterator.current.type==Token.Type.OPERATOR_OPEN_PAREN){
                            parens++
                        }
                        sub += iterator.current
                        iterator.next
                    }
                }
            }
        } else {
            throw SyntaxException("无法找到 function")
        }
    }

    override fun value(): Value {
        return context.findFunction(functionName)?.execute(args)!!
    }
}