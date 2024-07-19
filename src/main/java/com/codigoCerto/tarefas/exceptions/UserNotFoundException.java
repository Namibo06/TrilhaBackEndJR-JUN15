package com.codigoCerto.tarefas.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("Usuário não encontrado");
    }
}
