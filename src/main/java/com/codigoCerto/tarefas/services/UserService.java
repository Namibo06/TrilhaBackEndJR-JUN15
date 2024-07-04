package com.codigoCerto.tarefas.services;

import com.codigoCerto.tarefas.dtos.*;
import com.codigoCerto.tarefas.models.User;
import com.codigoCerto.tarefas.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder encoder;

    public ResponseApiMessageStatus createUserService(UserDTO userDTO){
        boolean existsByEmail = existsByEmailService(userDTO.getEmail());
        if (existsByEmail){
            throw new DataIntegrityViolationException("Email já existe,tente outro por favor");
        }

        User userModel = modelMapper.map(userDTO, User.class);
        userModel.setUsername(userDTO.getUsername());
        userModel.setEmail(userDTO.getEmail());
        userModel.setPassword(encoder.encode(userDTO.getPassword()));
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

    public void deleteUserByIdService(Long id){
        repository.deleteById(id);
    }

    public Boolean authenticateUser(LoginDTO loginDTO){
        Optional<User> optionalUser = repository.findByEmail(loginDTO.getEmail());
        boolean existsUser = repository.existsByEmailAndPassword(loginDTO.getEmail(),loginDTO.getPassword());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            boolean checkPassword = encoder.matches(loginDTO.getPassword(), user.getPassword());
            if (!checkPassword) {
                throw new BadCredentialsException("Senhas não batem");
            }
            return true;
        }
        return false;
    }

    public ResponseApiMessageStatus updateTokenById(String email,String token){
        User userModel = repository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("Usuário não encontrado"));

        userModel.setToken(token);
        repository.save(userModel);

        String message = "Token criado e atualizado com sucesso";
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
