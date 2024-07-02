package com.codigoCerto.tarefas.services;

import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.ResponsePasswordDTO;
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
        userDTO.setId(userModel.getId());
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

    public ResponseApiMessageStatus updateUserByIdService(Long id,ResponseUserDTO userDTO){
        boolean existsUser = existsUserById(id);
        if(!existsUser){
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        Optional<User> userModel = repository.findById(id);
        userModel.map(user -> {
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            repository.save(user);
            return user;
        });
        String message = "Usuário atualizado com sucesso";
        Integer status = 200;

        return new ResponseApiMessageStatus(message,status);
    }

    public ResponseApiMessageStatus updatePasswordByIdService(Long id, ResponsePasswordDTO passwordDTO){
        boolean existsUser = existsUserById(id);
        if(!existsUser){
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        Optional<User> userModel = repository.findById(id);
        userModel.map(user -> {
            user.setUsername(user.getUsername());
            user.setEmail(user.getEmail());
            user.setPassword(passwordDTO.getPassword());
            repository.save(user);
            return user;
        });

        String message = "Usuário atualizado com sucesso";
        Integer status = 200;

        return new ResponseApiMessageStatus(message,status);
    }

    public boolean existsUserById(Long id){
        return repository.existsById(id);
    }

    public Boolean existsByEmailService(String email){
        return repository.existsByEmail(email);
    }
}
