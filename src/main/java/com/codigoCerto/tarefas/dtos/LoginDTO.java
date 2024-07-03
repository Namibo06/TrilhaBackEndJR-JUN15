package com.codigoCerto.tarefas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    String token;
    String message;
    Integer status;
}
