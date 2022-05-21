package io.github.actar233.utils

import io.github.actar233.exception.SyntaxException
import io.github.actar233.lexical.Token

abstract class Value {

    enum class Type {
        NONE,//未初始化
        NUMBER,//数字
        STRING,//字符串
        BOOL,//布尔
        VOID //无返回的
    }

    protected open var attribute=HashMap<String,Value>()

    open fun findAttribute(key:String):Value{
        if(attribute.containsKey(key)){
            return attribute[key]!!
        }
        throw RuntimeException("不存在属性 $key")
    }

    // +
    abstract operator fun plus(other: Value): Value

    // -
    abstract operator fun minus(other: Value): Value

    // *
    abstract operator fun times(other: Value): Value

    // /
    abstract operator fun div(other: Value): Value

    abstract operator fun not(): Value

    abstract fun assign(other: Value)

    //大于
    abstract infix fun greater(other:Value):Value

    //大于等于
    abstract infix fun greaterEquals(other:Value):Value

    //小于
    abstract infix fun less(other:Value):Value

    //小于
    abstract infix fun lessEquals(other:Value):Value

    abstract infix fun equals(other: Value): Value

    abstract infix fun and(other: Value): Value

    abstract infix fun or(other: Value): Value

    abstract infix fun xor(other: Value): Value

    abstract fun isTrue():Boolean

    abstract fun isFalse():Boolean

    abstract fun value(): Any?

    companion object {

        fun parse(type: Type, data: String?): Value {
            if (type == Type.STRING) {
                return StringValue(data)
            }
            if (type == Type.NUMBER){
                return NumberValue(data)
            }
            if(type==Type.BOOL){
                return BoolValue( data)
            }
            throw SyntaxException("格式化异常")
        }

        fun parse(token: Token): Value {
            if (token.isValue()) {
                if (token.type == Token.Type.VALUE_NUMBER) {
                    return NumberValue(token.value)
                }
                if (token.type == Token.Type.VALUE_STRING) {
                    return StringValue(token.value)
                }
                if (token.type == Token.Type.VALUE_BOOL) {
                    return BoolValue(token.value)
                }
                if (token.type == Token.Type.VALUE_BOOL) {
                    return BoolValue(token.value)
                }
                if(token.type==Token.Type.VALUE_NULL){
                    return NullValue
                }
            }
            throw SyntaxException("格式化异常")
        }
    }
}
