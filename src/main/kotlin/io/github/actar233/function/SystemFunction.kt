package io.github.actar233.function

object SystemFunction {
    private val functions = HashMap<String, Function>()

    init {
        this += Println()
        this += Print()
        this += PI()
        this += ReadNumber()
        this += ReadLine()
    }

    private operator fun plusAssign(function: Function) {
        functions[function.name] = function
    }

    operator fun get(name: String): Function? {
        if (functions.containsKey(name)) {
            return functions[name]
        }
        return null
    }
}