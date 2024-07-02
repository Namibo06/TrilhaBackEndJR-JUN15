package com.codigoCerto.tarefas.services;

import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.UserDTO;
import com.codigoCerto.tarefas.models.User;
import com.codigoCerto.tarefas.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ResponseApiMessageStatus createUserService(UserDTO userDTO){
        boolean existsByEmail = existsByEmailService(userDTO.getEmail());
        if (existsByEmail){
            throw new DataIntegrityViolationException("Email já existe,tente outro por favor");
        }

        System.out.println("recebi exernamente");
        User userModel = modelMapper.map(userDTO, User.class);
        userModel.setUsername(userDTO.getUsername());
        userModel.setEmail(userDTO.getEmail());
        userModel.setPassword(userDTO.getPassword());
        repository.save(userModel);
        System.out.println("Salvei");
        String message = "Usuário criado com sucesso!";
        Integer status = 201;
        return new ResponseApiMessageStatus(message,status);
    }

    public Boolean existsByEmailService(String email){
        return repository.existsByEmail(email);
    }
}
