大学时候写得解释器，最近翻了出来。传上来防止以后找不到了。


```kotlin
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
```

### output:
```text
请输入你的名字: 张三
你好 张三
```