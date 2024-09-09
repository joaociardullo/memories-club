package com.devjoao.passagem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.InvalidPropertiesFormatException;

@ControllerAdvice
public class ExceptionGlobalHandler {
    @ExceptionHandler(value = {NumberFormatException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDTO> handleNumberFormatException(NumberFormatException ex) {
        var errorResponse = ErrorResponseDTO.builder()
                .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .message(ex.getMessage())
                .erro(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                .path("/passagem/cadastrarPassagem")
                .timesStamp(String.valueOf(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {InvalidPropertiesFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleInvalidPropertiesFormatException(InvalidPropertiesFormatException ex) {
        var errorResponse = ErrorResponseDTO.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .path("/passagem/cadastrarPassagem")
                .message(ex.getMessage())
                .erro(ex.getLocalizedMessage())
                .timesStamp(String.valueOf(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleNumberFormatException(IllegalArgumentException ex) {
        var errorResponse = ErrorResponseDTO.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message("CPF Cadastrado ja existe na base de dados: ")
                .path("/passagem/cadastrarPassagem")
                .timesStamp(String.valueOf(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CpfException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleCpfException(CpfException ex) {
        var errorResponse = ErrorResponseDTO.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message("CPF Cadastrado ja existe na base de dados: ")
                .path("/passagem/cadastrarPassagem")
                .erro(ex.getMessage())
                .timesStamp(String.valueOf(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {CpfNullException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleCpfNullException(CpfNullException ex) {
        var errorResponse = ErrorResponseDTO.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .path("/passagem/cadastrarPassagem")
                .message(ex.getMessage())
                .erro(ex.getLocalizedMessage())
                .timesStamp(String.valueOf(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}