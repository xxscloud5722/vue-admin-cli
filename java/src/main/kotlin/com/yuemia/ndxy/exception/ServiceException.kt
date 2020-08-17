package com.yuemia.ndxy.exception

class ServiceException : RuntimeException {
    constructor(message: ExceptionMessageEnum) : super(message.code + "\n" + message.message)

    constructor(message: String) : super(message)
}