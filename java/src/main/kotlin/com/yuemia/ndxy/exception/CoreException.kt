package com.yuemia.ndxy.exception

class CoreException : RuntimeException {
    constructor(message: ExceptionMessageEnum) : super(message.code + "\n" + message.message)

    constructor(message: String): super(message)
}