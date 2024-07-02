package com.codigoCerto.tarefas.controllers;

import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.UserDTO;
import com.codigoCerto.tarefas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<ResponseApiMessageStatus> createUser(@RequestBody UserDTO userDTO, UriComponentsBuilder uriBuilder){
        ResponseApiMessageStatus response = service.createUserService(userDTO);
        URI path = uriBuilder.path("/users/{id}").buildAndExpand(userDTO.getId()).toUri();

        return ResponseEntity.created(path).body(response);
    }
}
