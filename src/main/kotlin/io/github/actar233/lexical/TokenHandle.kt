package io.github.actar233.lexical

import io.github.actar233.utils.RecordIterator
import java.util.regex.Matcher

class TokenHandle(private val iterator: RecordIterator<Token> = RecordIterator<Token>()) {
    private class RegularHandle(val regular: String, vararg val callbacks: ((value: String, last: Token?, iterator: RecordIterator<Token>) -> Unit)?)

    private var matcher: Matcher? = null
    private var source: String? = null

    private var _position: Int? = null
    var position: Int
        get() {
            if (_position != null) {
                return _position!!
            }
            if (matcher != null) {
                return matcher!!.end()
            }
            return -1
        }
        set(value) {
            _position = value
        }

    private val patterns = ArrayList<RegularHandle>()
    fun register(regular: String, vararg callbacks: ((value: String, last: Token?, iterator: RecordIterator<Token>) -> Unit)?) {
        patterns += RegularHandle(regular, *callbacks)
        if (callbacks.size != regular.toPattern().matcher("").groupCount()) {
            throw RuntimeException("捕获组与回调数目不符合,涉及规则 $regular ")
        }
    }

    private fun getCallback(index: Int): ((value: String, last: Token?, iterator: RecordIterator<Token>) -> Unit)? {
        var count = 1
        for (pattern in patterns) {
            for (callback in pattern.callbacks) {
                if (index == count++) {
                    return callback
                }
            }
        }
        return null
    }

    fun execute(source: String): RecordIterator<Token> {
        val matcher = merge().toPattern().matcher(source)
        this.matcher = matcher
        this.source = source
        var last: Token? = null
        while (matcher.find()) {
            for (i in 1..matcher.groupCount()) {
                val value = matcher.group(i)
                if (value != null) {
                    val callback = getCallback(i)
                    if (callback != null) {
                        val current = RecordIterator<Token>()
                        callback(value, last, current)
                        iterator += current
                        last = current.current
                    }
                }
            }
            if (_position != null) {
                matcher.region(_position!!,source.length)
                _position = null
            }
        }
        this.matcher = null
        this.source = null
        return iterator
    }

    private fun callbackCount(): Int {
        var count = 0
        for (pattern in patterns) {
            for (callback in pattern.callbacks) {
                count++
            }
        }
        return count
    }

    private fun merge(): String {
        return if (patterns.size > 0) {
            val len = patterns.size - 1
            val builder = StringBuilder()
            for (i in 0..len) {
                builder.append(patterns[i].regular)
                if (i < len) {
                    builder.append("|")
                }
            }
            builder.toString()
        } else {
            ""
        }
    }
}
