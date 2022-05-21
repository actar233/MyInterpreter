package io.github.actar233.utils

import io.github.actar233.exception.SyntaxException

class BoolValue:Value{
    private var data:Boolean?=null
    constructor(data:Boolean?){
        this.data=data
    }
    constructor(data:String?){
        when (data) {
            null -> this.data=null
            "true" -> this.data=true
            "false" -> this.data=false
        }
    }
    // +
    override operator fun plus(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 plus 操作")
    }

    // -
    override operator fun minus(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 minus 操作")
    }

    // *
    override operator fun times(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 times 操作")
    }

    // /
    override operator fun div(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 div 操作")
    }

    override operator fun not(): Value{
        return BoolValue(!(this.data as Boolean))
    }

    override fun assign(other: Value) {
        if(other==NullValue){
            this.data=null
            return
        }
        if (other is BoolValue) {
            this.data = other.data
        } else {
            throw SyntaxException("类型错误,无法进行 assign 操作")
        }
    }

    //大于
    override infix fun greater(other:Value):Value{
        throw SyntaxException("类型错误,无法进行 greater 操作")
    }

    //大于等于
    override infix fun greaterEquals(other:Value):Value{
        throw SyntaxException("类型错误,无法进行 greaterEquals 操作")
    }

    //小于
    override infix fun less(other:Value):Value{
        throw SyntaxException("类型错误,无法进行 less 操作")
    }

    //小于
    override infix fun lessEquals(other:Value):Value{
        throw SyntaxException("类型错误,无法进行 lessEquals 操作")
    }

    override infix fun equals(other: Value): Value {
        if (this.data == null){
            return NullValue.equals(other)
        }
        if(other is BoolValue && this.data==other.data){
            return BoolValue(true)
        }
        return BoolValue(false)
    }

    override infix fun and(other: Value): Value {
        if(other is BoolValue){
            return BoolValue(this.data as Boolean and other.data as Boolean)
        }
        throw SyntaxException("类型错误,无法进行 and 操作")
    }

    override infix fun or(other: Value): Value {
        if(other is BoolValue){
            return BoolValue(this.data as Boolean or other.data as Boolean)
        }
        throw SyntaxException("类型错误,无法进行 or 操作")
    }

    override infix fun xor(other: Value): Value {
        if(other is BoolValue){
            return BoolValue(this.data as Boolean xor other.data as Boolean)
        }
        throw SyntaxException("类型错误,无法进行 xor 操作")
    }

    override fun isTrue(): Boolean {
        return this.data==true
    }

    override fun isFalse(): Boolean {
        return this.data==false
    }

    override fun value(): Any? {
        return data
    }

    override fun toString(): String {
        return data.toString()
    }
}