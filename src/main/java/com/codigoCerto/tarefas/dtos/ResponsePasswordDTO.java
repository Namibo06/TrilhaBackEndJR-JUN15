package com.codigoCerto.tarefas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePasswordDTO {
    Long id;
    String username;
    String email;
    String password;
}
