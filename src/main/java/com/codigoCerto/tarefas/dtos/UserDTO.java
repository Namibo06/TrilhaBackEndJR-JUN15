package com.codigoCerto.tarefas.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String password;
}
