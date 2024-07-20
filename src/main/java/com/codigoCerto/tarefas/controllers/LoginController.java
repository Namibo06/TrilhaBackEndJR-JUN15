package com.codigoCerto.tarefas.controllers;

import com.codigoCerto.tarefas.dtos.*;
import com.codigoCerto.tarefas.services.LoginService;
import com.codigoCerto.tarefas.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
        Boolean existsUser = userService.authenticateUser(userDTO);

        String tokenCreated = loginService.createToken();
        ResponseApiMessageStatus updateUserTokenById = userService.updateTokenById(userDTO.getEmail(),tokenCreated);
        String MESSAGE_OK = updateUserTokenById.getMessage();
        Integer STATUS_OK = updateUserTokenById.getStatus();
        ResponseTokenDTO response = new ResponseTokenDTO(tokenCreated,MESSAGE_OK,STATUS_OK);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{token}")
    @Operation(summary = "Secure Endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ResponseApiMessageStatus> verifyToken(@PathVariable String token){
        loginService.verifyToken(token);

        String message="Token verificado com sucesso!";
        Integer status = 200;
        ResponseApiMessageStatus response = new ResponseApiMessageStatus(message,status);

        return ResponseEntity.ok(response);
    }
}
