package com.codigoCerto.tarefas.services;

import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.ResponseUserDTO;
import com.codigoCerto.tarefas.dtos.UserDTO;
import com.codigoCerto.tarefas.models.User;
import com.codigoCerto.tarefas.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseApiMessageStatus createUserService(UserDTO userDTO){
        boolean existsByEmail = existsByEmailService(userDTO.getEmail());
        if (existsByEmail){
            throw new DataIntegrityViolationException("Email já existe,tente outro por favor");
        }

        User userModel = modelMapper.map(userDTO, User.class);
        userModel.setUsername(userDTO.getUsername());
        userModel.setEmail(userDTO.getEmail());
        userModel.setPassword(userDTO.getPassword());
        repository.save(userModel);

        String message = "Usuário criado com sucesso!";
        Integer status = 201;
        return new ResponseApiMessageStatus(message,status);
    }

    public ResponseUserDTO findUserById(Long id){
        User userModel = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("ID de usuário não encontrado"));
        ResponseUserDTO userDTO = new ResponseUserDTO();
        userDTO.setUsername(userModel.getUsername());
        userDTO.setEmail(userModel.getEmail());
        return userDTO;
    }

    public List<UserDTO> findAll(){
        List<User> userList=repository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user:userList){
            UserDTO userDTO=modelMapper.map(user,UserDTO.class);
            userDTOList.add(userDTO);
        }

        return userDTOList;
    }

    public Boolean existsByEmailService(String email){
        return repository.existsByEmail(email);
    }
}
