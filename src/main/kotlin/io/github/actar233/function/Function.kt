package io.github.actar233.function

import io.github.actar233.utils.RecordIterator
import io.github.actar233.utils.Value

interface Function{
    val name:String;
    fun execute(args:RecordIterator<Value>):Value;
}