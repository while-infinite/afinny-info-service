package by.afinny.infoservice.exception.handler;

import by.afinny.infoservice.exception.BytesFromFileException;
import by.afinny.infoservice.exception.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> serverExceptionHandler(Exception e) {
        log.error("Internal server error", e);
        return createResponseEntity(
                HttpStatus.INTERNAL_SERVER_ERROR,
                new ErrorDto(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                        "Internal server error"));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> badRequestExceptionHandler(Exception e) {
        log.error("Bad request", e);
        return createResponseEntity(
                HttpStatus.BAD_REQUEST,
                new ErrorDto(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                        "Bad request"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BytesFromFileException.class)
    public ResponseEntity<ErrorDto> bytesFromFileException(Exception e){
        log.error("Bad request: {}", e.getMessage());
        return createResponseEntity(
                HttpStatus.BAD_REQUEST,
                new ErrorDto(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                        e.getMessage()));
    }

    private ResponseEntity<ErrorDto> createResponseEntity(HttpStatus status, ErrorDto errorDto) {
        return ResponseEntity.status(status)
                .header("Content-Type", "application/json")
                .body(errorDto);
    }
}
