package io.github.actar233.utils

import java.util.*

class RecordIterator<T> {
    private val list: Stack<T>
    private var _sign = 0
    private var _position = 0
    val position
        get() = _position
    val size: Int
        get() = list.size
    val surplus: Int
        get() = list.size - position
    val sign: Int
        get() {
            val sg = _sign
            _sign = 0
            return sg
        }

    val current: T
        get() {
            if (_position - 1 < 0) {
                throw ArrayIndexOutOfBoundsException(",无法进行取出")
            }
            return list[_position - 1]
        }

    val next: T
        get() {
            _sign++
            return list[_position++]
        }

    val hasNext: Boolean
        get() = list.size > _position

    constructor() {
        this.list = Stack<T>()
    }

    constructor(list: Stack<T>) {
        this.list = list
    }

    fun watch(count: Int=1): T {
        if (_position -1 + count > list.size) {
            throw ArrayIndexOutOfBoundsException("下标越位 ,position+count= ${_position -1 + count} > size:${list.size}")
        }
        return list[_position + count-1]
    }

    fun add(token: T) {
        list += token
    }

    fun back(back: Int = sign) {
        if (_position - back < 0) {
            throw ArrayIndexOutOfBoundsException("position:$_position - back:$back= ${_position - back} < 0 ,无法进行back")
        }
        _position -= back
    }

    fun range(start: Int, count: Int): RecordIterator<T> {
        if (start + count > list.size) {
            throw ArrayIndexOutOfBoundsException("超过范围")
        }
        val tokenIterator = RecordIterator<T>()
        for (i in start until (start + count)) {
            tokenIterator += list[i]
        }
        return tokenIterator
    }

    fun range(count: Int): RecordIterator<T> {
        return range(position, count)
    }

    fun clear() {
        list.clear()
        _position = 0
        _sign = 0
    }

    //运算符重载

    // +=
    operator fun plusAssign(token: T) {
        add(token)
    }

    operator fun minusAssign(back: Int) {
        back(back)
    }

    operator fun plusAssign(current: RecordIterator<T>) {
        while (current.hasNext) {
            this += current.next
        }
    }

    override fun toString(): String {
        return if (size > 0) {
            val len = size - 1
            val builder = StringBuilder()
            builder.append("TokenIterator[")
            for (i in position..len) {
                builder.append(list[i])
                if (i < len) {
                    builder.append(",")
                }
            }
            builder.append("]")
            builder.toString()
        } else {
            "TokenIterator[]"
        }
    }

    fun clone():RecordIterator<T>{
        @Suppress("UNCHECKED_CAST")
        return RecordIterator(this.list.clone() as Stack<T>)
    }
}