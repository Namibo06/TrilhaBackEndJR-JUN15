package com.codigoCerto.tarefas.controllers;

import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.ResponsePasswordDTO;
import com.codigoCerto.tarefas.dtos.ResponseUserDTO;
import com.codigoCerto.tarefas.dtos.UserDTO;
import com.codigoCerto.tarefas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable Long id){
        ResponseUserDTO userById = service.findUserById(id);
        return ResponseEntity.ok(userById);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDTOList = service.findAll();
        return ResponseEntity.ok(userDTOList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseApiMessageStatus> updateUserById(@PathVariable Long id,@RequestBody ResponseUserDTO userDTO){
        ResponseApiMessageStatus response = service.updateUserByIdService(id,userDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("updatePassword/{id}")
    public ResponseEntity<ResponseApiMessageStatus> updatePasswordById(@PathVariable Long id, @RequestBody ResponsePasswordDTO passwordDTO){
        ResponseApiMessageStatus response = service.updatePasswordByIdService(id,passwordDTO);

        return ResponseEntity.ok(response);
    }
}
