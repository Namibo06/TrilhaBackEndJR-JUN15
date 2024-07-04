package com.codigoCerto.tarefas.controllers;

import com.codigoCerto.tarefas.dtos.LoginDTO;
import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.UserDTO;
import com.codigoCerto.tarefas.services.LoginService;
import com.codigoCerto.tarefas.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticate;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseApiMessageStatus> login(@RequestBody UserDTO userDTO){
        Boolean existsUser = userService.authenticateUser(userDTO.getEmail(),userDTO.getPassword());
        if(!existsUser){
            throw new EntityNotFoundException("Usuário não existe");
        }
        String tokenCreated = loginService.createToken();
        ResponseApiMessageStatus updateUserTokenById = userService.updateTokenById(userDTO.getId(),tokenCreated);

        return ResponseEntity.ok(updateUserTokenById);
    }
}
