package io.github.actar233.utils

import io.github.actar233.exception.SyntaxException
import java.math.BigDecimal

class StringValue : Value {
    private var data: String? = null

    constructor(data: String?) {
        this.data = data
        initAttribute()
    }

    private fun initAttribute() {
        attribute["length"] = NumberValue(data?.length ?: 0)
    }

    override operator fun plus(other: Value): Value {
        if (other.value() == null) {
            return StringValue(this.data + "null")
        }
        if (other is StringValue) {
            return StringValue(this.data + other.data)
        }
        if (other is NumberValue) {
            return StringValue(this.data + other.value() as BigDecimal)
        }
        if (other is BoolValue) {
            return StringValue(this.data as String + other.value() as Boolean)
        }
        throw SyntaxException("类型错误,无法进行 plus 操作")
    }

    // -
    override operator fun minus(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 minus 操作")
    }

    // *
    override operator fun times(other: Value): Value {
        if(other is NumberValue){
            val number = other.value() as BigDecimal? ?: throw SyntaxException("参数异常,不能为空")
            val i = number.toBigInteger()?:throw SyntaxException("参数异常,不能为空")
            var str = ""
            for (m in 0..i.toInt()){
                str += this.data;
            }
            return StringValue(str)
        }
        throw SyntaxException("类型错误,无法进行 times 操作")
    }

    // /
    override operator fun div(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 div 操作")
    }

    override operator fun not(): Value {
        throw SyntaxException("类型错误,无法进行 not 操作")
    }

    override fun assign(other: Value) {
        if (other == NullValue) {
            this.data = null
            this.attribute["length"] = NumberValue(0)
            return
        }
        if (other is StringValue) {
            this.data = other.data
            this.attribute = other.attribute
        } else {
            throw SyntaxException("类型错误,无法进行 assign 操作")
        }
    }

    //大于
    override infix fun greater(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 greater 操作")
    }

    //大于等于
    override infix fun greaterEquals(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 greaterEquals 操作")
    }

    //小于
    override infix fun less(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 less 操作")
    }

    //小于
    override infix fun lessEquals(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 lessEquals 操作")
    }

    override infix fun equals(other: Value): Value {
        if (this.data == null) {
            return NullValue.equals(other)
        }
        if (other is StringValue && this.data == other.data) {
            return BoolValue(true)
        }
        return BoolValue(false)
    }

    override infix fun and(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 and 操作")
    }

    override infix fun or(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 or 操作")
    }

    override infix fun xor(other: Value): Value {
        throw SyntaxException("类型错误,无法进行 xor 操作")
    }

    override fun isTrue(): Boolean {
        throw SyntaxException("不是Bool值")
    }

    override fun isFalse(): Boolean {
        throw SyntaxException("不是Bool值")
    }

    override fun value(): Any? {
        return data
    }

    override fun toString(): String {
        return data.toString()
    }
}