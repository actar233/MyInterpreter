package io.github.actar233.function

import io.github.actar233.utils.RecordIterator
import io.github.actar233.utils.Value

 abstract class NativeFunction: Function {
    abstract override fun execute(args: RecordIterator<Value>):Value;
}