package com.codigoCerto.tarefas.exceptions;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(){
        super("Token não encontrado");
    }
}
