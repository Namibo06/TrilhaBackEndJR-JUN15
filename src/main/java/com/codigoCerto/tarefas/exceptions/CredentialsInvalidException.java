package com.codigoCerto.tarefas.exceptions;

public class CredentialsInvalidException extends RuntimeException{
    public CredentialsInvalidException(String message){
        super(message);
    }
}
