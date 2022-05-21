package io.github.actar233.utils

import io.github.actar233.exception.SyntaxException

object NullValue : Value() {

    override fun plus(other: Value): Value {
        throw SyntaxException("空指针异常")
    }

    override fun minus(other: Value): Value {
        throw SyntaxException("空指针异常")
    }

    override fun times(other: Value): Value {
        throw SyntaxException("空指针异常")
    }

    override fun div(other: Value): Value {
        throw SyntaxException("空指针异常")
    }

    override fun not(): Value {
        throw SyntaxException("空指针异常")
    }

    override fun assign(other: Value) {
        throw SyntaxException("空指针异常")
    }

    override fun greater(other: Value): Value {
        throw SyntaxException("空指针异常")
    }

    override fun greaterEquals(other: Value): Value {
        throw SyntaxException("空指针异常")
    }

    override fun less(other: Value): Value {
        throw SyntaxException("空指针异常")
    }

    override fun lessEquals(other: Value): Value {
        throw SyntaxException("空指针异常")
    }

    override fun equals(other: Value): Value {
        return BoolValue(this == other)
    }

    override fun and(other: Value): Value {
        throw SyntaxException("空指针异常")
    }

    override fun or(other: Value): Value {
        throw SyntaxException("空指针异常")
    }

    override fun xor(other: Value): Value {
        throw SyntaxException("空指针异常")
    }

    override fun isTrue(): Boolean {
        throw SyntaxException("空指针异常")
    }

    override fun isFalse(): Boolean {
        throw SyntaxException("空指针异常")
    }

    override fun value(): Any? {
        return null
    }
}