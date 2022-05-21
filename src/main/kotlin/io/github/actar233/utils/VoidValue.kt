package io.github.actar233.utils

import io.github.actar233.exception.SyntaxException

class VoidValue:Value(){

    override fun plus(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun minus(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun times(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun div(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun not(): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun assign(other: Value) {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun greater(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun greaterEquals(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun less(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun lessEquals(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun equals(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun and(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun or(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun xor(other: Value): Value {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun isTrue(): Boolean {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun isFalse(): Boolean {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun value(): Any? {
        throw SyntaxException("Void 无法进行任何操作")
    }

    override fun toString(): String {
        throw SyntaxException("Void 无法进行任何操作")
    }
}