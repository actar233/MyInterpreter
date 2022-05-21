package io.github.actar233.syntax

import io.github.actar233.exception.SyntaxException
import io.github.actar233.function.SystemFunction
import io.github.actar233.function.Function
import io.github.actar233.utils.Value

class Context(val superContext: Context?=null){
    private val vars=HashMap<String,Value>()
    private val functions=HashMap<String, Function>()
    operator fun get(key:String):Value{
        if(vars.containsKey(key)){
            return vars[key]!!
        }
        if(superContext!=null){
            return superContext[key]
        }
        throw SyntaxException("不存在变量 $key")
    }
    operator fun set(key:String, value:Value){
        vars[key]=value
    }
    fun containsKey(key:String):Boolean{
        return vars.containsKey(key)
    }
    fun findFunction(name:String): Function?{
        if(functions.containsKey(name)){
            return functions[name]
        }
        if(superContext!=null){
            return superContext.findFunction(name)
        }
        return SystemFunction[name]
    }
}