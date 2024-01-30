package ly.abit.shortener

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [ExceptionControllerAdvice::class])
class ExceptionControllerAdvice {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [ClassNotFoundException::class])
    fun handleNotFoundException(e: ClassNotFoundException): ErrorRes {
        return ErrorRes(e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleIllegalArgumentException(e: IllegalArgumentException): ErrorRes {
        return ErrorRes(e.message)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception): ErrorRes {
        logger.error("server error occurs", e)
        return ErrorRes(e.message)
    }
}

data class ErrorRes(val message: String?)
