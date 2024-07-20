package com.codigoCerto.tarefas.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(){
        super("Email já existe");
    }
}
