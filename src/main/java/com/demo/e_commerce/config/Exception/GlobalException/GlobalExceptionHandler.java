package com.demo.e_commerce.config.Exception.GlobalException;

import com.demo.e_commerce.config.Exception.CustomeException.*;
import com.demo.e_commerce.dto.commonDto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setSuccess(false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDto> handleUnauthorizedException(UnauthorizedException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setSuccess(false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> handleRuntimeException(RuntimeException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setSuccess(false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<ExceptionDto> handleCategoryNotFound(CategoryNotFound e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setSuccess(false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NegativePriceException.class)
    public ResponseEntity<ExceptionDto> handleNegativePriceException(NegativePriceException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setSuccess(false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingDataException.class)
    public ResponseEntity<ExceptionDto> handleMissingDataException(MissingDataException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setSuccess(false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setSuccess(false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DBError.class)
    public ResponseEntity<ExceptionDto> handleDBError(DBError e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setSuccess(false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidQunatityException.class)
    public ResponseEntity<ExceptionDto> handleInvalidQunatityException(InvalidQunatityException e) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        exceptionDto.setSuccess(false);
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
}
