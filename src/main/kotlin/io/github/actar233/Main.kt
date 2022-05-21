package io.github.actar233

import io.github.actar233.lexical.Lexer
import io.github.actar233.syntax.statement.Block

fun main(args: Array<String>) {

    val text = """
        String hello = "你好 ";
        String name = null;
        while(name == null) {
            print("请输入你的名字: ");
            name = readLine();
        }
        println(hello + name);
    """.trimIndent()

    val iterator = Lexer(text).parse()
    Block(iterator).execute()

}