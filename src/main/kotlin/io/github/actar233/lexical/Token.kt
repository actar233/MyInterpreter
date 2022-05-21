package io.github.actar233.lexical

class Token(val type: Token.Type, val value: String) {
    enum class Type() {
        /* 常量值 */
        VALUE_NUMBER,//数字
        VALUE_STRING,//字符串
        VALUE_BOOL,//布尔
        VALUE_NULL,//空值

        ID,//变量编号
        CALL,//调用名字

        /* 关键字 */
        /* 基础类型 */
        KEYWORD_NUMBER,//数字类型
        KEYWORD_STRING,//字符串类型
        KEYWORD_BOOL,//布尔类型

        KEYWORD_IF, //如果

        KEYWORD_ELSE_IF, // 否则如果

        KEYWORD_ELSE,//否则

        KEYWORD_WHILE, // while 循环

        /* 符号 */
        SYMBOL_COMMA,// 逗号
        SYMBOL_COLON,//分号

        SYMBOL_OPEN_BRACE,// {
        SYMBOL_CLOSE_BRACE,// }

        /** 运算符 */

        OPERATOR_DOT, //点 .

        /* 算数运算 */
        OPERATOR_OPEN_PAREN,        // 左小括号
        OPERATOR_CLOSE_PAREN,       // 右小括号
        OPERATOR_PLUS,              // 加
        OPERATOR_MINUS,             // 减
        OPERATOR_MULTIPLY,          // 乘
        OPERATOR_DIVIDE,            // 除
        OPERATOR_POSITIVE,          // 正号
        OPERATOR_NEGATIVE,          // 负号

        OPERATOR_GREATER,// 大于 >
        OPERATOR_GREATER_EQUALS,//大于等于 >=
        OPERATOR_LESS,//小于 <
        OPERATOR_LESS_EQUALS, // 小于等于 <=
        OPERATOR_EQUALS,//相等 ==
        OPERATOR_NOT_EQUALS,//不等 !=

        OPERATOR_NOT,// not !
        OPERATOR_LOGIC_AND,// double and &&
        OPERATOR_AND,// and &
        OPERATOR_LOGIC_OR, // double or ||
        OPERATOR_OR, // or |
        OPERATOR_XOR, // xor ^

        /* 赋值 */
        OPERATOR_ASSIGNMENT,        // 赋值
    }

    override fun toString(): String {
        return "Token(type=$type, value='$value')"
    }

    /**
     * 是否为操作符
     */
    fun isOperator(): Boolean {
        return type.toString().startsWith("OPERATOR")
    }

    /**
     * 是否为数据
     */
    fun isValue(): Boolean {
        return type.toString().startsWith("VALUE")
    }

    /**
     * 是否为符号
     */
    fun isSymbol(): Boolean {
        return type.toString().startsWith("SYMBOL");
    }

    /**
     * 是否为关键字
     */
    fun isKeyword(): Boolean {
        return type.toString().startsWith("KEYWORD")
    }

    /**
     * 是否为ID
     */
    fun isID(): Boolean {
        return type == Type.ID
    }

    /**
     * 是否为CALL
     */
    fun isCall(): Boolean {
        return type == Type.CALL
    }
}