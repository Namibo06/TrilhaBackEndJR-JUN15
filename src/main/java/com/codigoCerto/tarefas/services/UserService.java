package com.codigoCerto.tarefas.services;

import com.codigoCerto.tarefas.dtos.UserDTO;
import com.codigoCerto.tarefas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public UserDTO createUserService(UserDTO userDTO){

    }
}
