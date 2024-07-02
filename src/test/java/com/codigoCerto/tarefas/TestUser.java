package com.codigoCerto.tarefas;

import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.UserDTO;
import com.codigoCerto.tarefas.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class TestUser {
    @Autowired
    private UserService service;

    @Test
    public void testCreateUser(){
        UserDTO userDTO = new UserDTO(null,"Joca","joca@gmail.com","123",null);
        //ResponseApiMessageStatus response = service.createUserService(userDTO);
        assertThrowsExactly(DataIntegrityViolationException.class,()->{
            service.createUserService(userDTO);
        });
    }
}
