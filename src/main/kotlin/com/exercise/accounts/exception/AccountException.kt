package com.exercise.accounts.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class CustomerAlreadyExistException(
    message: String,
) : RuntimeException() {
    val responseMessage: String = message
}
