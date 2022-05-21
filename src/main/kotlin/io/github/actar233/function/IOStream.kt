package io.github.actar233.function

import io.github.actar233.exception.SyntaxException
import io.github.actar233.utils.*
import java.util.*

private val scanner = Scanner(System.`in`)

class ReadLine : NativeFunction() {
    override val name: String = "readLine"
    override fun execute(args: RecordIterator<Value>): Value {
        return if (args.size == 0) {
            try {
                StringValue(scanner.next())
            } catch (e: Exception) {
                NullValue
            }
        } else {
            throw SyntaxException("参数异常")
        }
    }
}

class ReadNumber : NativeFunction() {
    override val name: String = "readNumber"
    override fun execute(args: RecordIterator<Value>): Value {
        return if (args.size == 0) {
            try {
                NumberValue(scanner.nextBigDecimal())
            } catch (e: Exception) {
                NullValue
            }
        } else {
            throw SyntaxException("参数异常")
        }
    }
}

class Println : NativeFunction() {
    override val name: String = "println"
    override fun execute(args: RecordIterator<Value>): Value {
        if (args.size == 1) {
            val data = args.next
            println(data.value().toString())
            return VoidValue()
        } else {
            throw SyntaxException("参数异常")
        }
    }
}

class Print : NativeFunction() {
    override val name: String = "print"
    override fun execute(args: RecordIterator<Value>): Value {
        if (args.size == 1) {
            val data = args.next
            print(data.value().toString())
            return VoidValue()
        } else {
            throw SyntaxException("参数异常")
        }
    }
}

class PI : NativeFunction() {
    override val name: String = "PI"
    override fun execute(args: RecordIterator<Value>): Value {
        if (args.size == 0) {
            return Value.parse(Value.Type.NUMBER, "3.1415926")
        } else {
            throw SyntaxException("参数异常")
        }
    }
}