package com.codigoCerto.tarefas.exceptions;

import com.codigoCerto.tarefas.dtos.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> UserNotFoundException(EntityNotFoundException ex){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), "Registro não encontrado");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
