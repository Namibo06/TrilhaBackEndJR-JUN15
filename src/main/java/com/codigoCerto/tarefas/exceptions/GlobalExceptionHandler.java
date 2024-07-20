package com.codigoCerto.tarefas.exceptions;

import com.codigoCerto.tarefas.dtos.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegisterNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleRegisterNotFoundException(RegisterNotFoundException ex){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), "Registro não encontrado");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CredentialsInvalidException.class)
    public ResponseEntity<ErrorResponseDTO> handlePasswordWrongException(CredentialsInvalidException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), "Erro nas credenciais");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), "Email já ativo no sistema");
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleTokenNotFoundException(TokenNotFoundException ex){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), "Não autorizado");
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(ValidationException ex){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(), "Erro na validação");
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
