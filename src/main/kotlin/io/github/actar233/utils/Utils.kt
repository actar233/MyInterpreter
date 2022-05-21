package io.github.actar233.utils

fun Any.at(vararg args:Any?):Boolean{
    return args.any { this== it }
}

fun Any.uat(vararg args:Any?):Boolean{
    return args.any { this!= it }
}

fun Array<String>.merge(interval:String):String{
    if(size>0){
        val len=size-1
        val builder = StringBuilder()
        for(i in 0..len){
            builder.append(this[i])
            if(i<len){
                builder.append(interval)
            }
        }
        return builder.toString()
    }else{
        return ""
    }
}