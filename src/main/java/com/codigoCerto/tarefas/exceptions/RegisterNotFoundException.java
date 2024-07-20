package com.codigoCerto.tarefas.exceptions;

public class RegisterNotFoundException extends RuntimeException{
    public RegisterNotFoundException(String message){
        super(message);
    }
}
