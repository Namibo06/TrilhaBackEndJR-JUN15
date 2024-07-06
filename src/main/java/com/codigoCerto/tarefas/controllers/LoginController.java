package com.codigoCerto.tarefas.controllers;

import com.codigoCerto.tarefas.dtos.*;
import com.codigoCerto.tarefas.services.LoginService;
import com.codigoCerto.tarefas.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
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
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Houve alguma falha ao autenticar usuário: "+e);
        }catch (Exception e){
            throw new InternalException("Erro interno: "+e);
        }
    }

    @PostMapping("/{token}")
    public ResponseEntity<ResponseApiMessageStatus> verifyToken(@PathVariable String token){
        loginService.verifyToken(token);

        String message="Token verificado com sucesso!";
        Integer status = 200;
        ResponseApiMessageStatus response = new ResponseApiMessageStatus(message,status);

        return ResponseEntity.ok(response);
    }
}
