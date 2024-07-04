package com.codigoCerto.tarefas.controllers;

import com.codigoCerto.tarefas.dtos.LoginDTO;
import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.ResponseTokenDTO;
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
    public ResponseEntity<ResponseTokenDTO> login(@RequestBody LoginDTO userDTO){
        try{
            Boolean existsUser = userService.authenticateUser(userDTO);
            if(!existsUser){
                throw new EntityNotFoundException("Usuário não existe");
            }
            String tokenCreated = loginService.createToken();
            ResponseApiMessageStatus updateUserTokenById = userService.updateTokenById(userDTO.getEmail(),tokenCreated);
            String MESSAGE_OK = updateUserTokenById.getMessage();
            Integer STATUS_OK = updateUserTokenById.getStatus();
            ResponseTokenDTO response = new ResponseTokenDTO(tokenCreated,MESSAGE_OK,STATUS_OK);

            return ResponseEntity.ok(response);
        }catch (Exception e){
            String MESSAGE_FAILED = "Houve alguma falha ao autenticar usuário: "+e;
            Integer STATUS_FAILED = 400;

            ResponseTokenDTO response = new ResponseTokenDTO(null,MESSAGE_FAILED,STATUS_FAILED);
            return ResponseEntity.ok(response);
        }
    }
}
