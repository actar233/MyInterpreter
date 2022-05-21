package io.github.actar233.lexical

class EscapeCharacterHandle(val source: String,val start: Int) {
    private val charsMapping = HashMap<Char, String>()
    private var _position = 0
    private var value=StringBuilder()
    val position: Int
        get() = _position

    init {
        _position=start
        charsMappingInit()
        matching()
    }

    private fun charsMappingInit() {
        charsMapping['b'] = "\b"
        charsMapping['n'] = "\n"
        charsMapping['r'] = "\r"
        charsMapping['t'] = "\t"
        charsMapping['\\'] = "\\"
        charsMapping['\''] = "\'"
        charsMapping['"'] = "\""
    }

    private fun matching(){
        var scape= false
        while (true){
            val c = source[_position++]
            if(scape){
                if(charsMapping.containsKey(c)){
                    value.append(charsMapping[c])
                }else {
                    throw RuntimeException("格式化异常 ,char=$c")
                }
                scape=false
                continue
            }
            if(c=='\\'){
                scape=true
                continue
            }
            if(c=='\"'){
                break
            }
            value.append(c)
        }
    }

    fun value(): String {
        return value.toString()
    }
}