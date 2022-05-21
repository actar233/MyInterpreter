package io.github.actar233.syntax.statement

import io.github.actar233.syntax.Context

interface Statement {
    var canBreak:Boolean
    fun execute(context: Context=Context());
}