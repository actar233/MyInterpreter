package io.github.actar233.utils

import io.github.actar233.exception.SyntaxException
import java.math.BigDecimal

class NumberValue : Value {
    private var data: BigDecimal? = null

    constructor(data: BigDecimal?) {
        this.data = data
    }

    constructor(data: String?) {
        if (data == null) {
            this.data = null
        } else {
            this.data = BigDecimal(data)
        }
    }

    constructor(data: Int) {
        this.data=BigDecimal(data)
    }

    override operator fun plus(other: Value): Value {
        if (other is NumberValue) {
            return NumberValue(this.data as BigDecimal + other.data as BigDecimal)
        }
        throw SyntaxException("类型错误,无法进行 plus 操作")
    }

    // -
    override operator fun minus(other: Value): Value {
        if (other is NumberValue) {
            return NumberValue(this.data as BigDecimal - other.data as BigDecimal)
        }
        throw SyntaxException("类型错误,无法进行 minus 操作")
    }

    // *
    override operator fun times(other: Value): Value {
        if (other is NumberValue) {
            return NumberValue(this.data as BigDecimal * other.data as BigDecimal)
        }
        throw SyntaxException("类型错误,无法进行 times 操作")
    }

    // /
    override operator fun div(other: Value): Value {
        if (other is NumberValue) {
            return NumberValue(this.data as BigDecimal / other.data as BigDecimal)
        }
        throw SyntaxException("类型错误,无法进行 div 操作")
    }

    override operator fun not(): Value {
        throw SyntaxException("类型错误,无法进行 not 操作")
    }

    override fun assign(other: Value) {
        if(other==NullValue){
            this.data=null
            return
        }
        if (other is NumberValue) {
            this.data = other.data
        } else {
            throw SyntaxException("类型错误,无法进行 assign 操作")
        }
    }

    //大于
    override infix fun greater(other: Value): Value {
        if (other is NumberValue) {
            if (this.data as BigDecimal > other.data as BigDecimal) {
                return BoolValue(true)
            } else {
                return BoolValue(false)
            }
        }
        throw SyntaxException("类型错误,无法进行 greater 操作")
    }

    //大于等于
    override infix fun greaterEquals(other: Value): Value {
        if (other is NumberValue) {
            if (this.data as BigDecimal >= other.data as BigDecimal) {
                return BoolValue(true)
            } else {
                return BoolValue(false)
            }
        }
        throw SyntaxException("类型错误,无法进行 greaterEquals 操作")
    }

    //小于
    override infix fun less(other: Value): Value {
        if (other is NumberValue) {
            return if (this.data as BigDecimal >= other.data as BigDecimal) {
                BoolValue(false)
            } else {
                BoolValue(true)
            }
        }
        throw SyntaxException("类型错误,无法进行 less 操作")
    }

    //小于
    override infix fun lessEquals(other: Value): Value {
        if (other is NumberValue) {
            if (this.data as BigDecimal > other.data as BigDecimal) {
                return BoolValue(false)
            } else {
                return BoolValue(true)
            }
        }
        throw SyntaxException("类型错误,无法进行 lessEquals 操作")
    }

    override infix fun equals(other: Value): Value {
        if (this.data == null) {
            return NullValue.equals(other)
        }
        if (other is NumberValue && this.data == other.data) {
            return BoolValue(true)
        }
        return BoolValue(false)
    }

    override infix fun and(other: Value): Value {
        if (other is NumberValue) {
            return NumberValue(((this.data as BigDecimal).toBigInteger() and (other.data as BigDecimal).toBigInteger()).toBigDecimal())
        }
        throw SyntaxException("类型错误,无法进行 and 操作")
    }

    override infix fun or(other: Value): Value {
        if (other is NumberValue) {
            return NumberValue(((this.data as BigDecimal).toBigInteger() or (other.data as BigDecimal).toBigInteger()).toBigDecimal())
        }
        throw SyntaxException("类型错误,无法进行 or 操作")
    }

    override infix fun xor(other: Value): Value {
        if (other is NumberValue) {
            return NumberValue(((this.data as BigDecimal).toBigInteger() xor (other.data as BigDecimal).toBigInteger()).toBigDecimal())
        }
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