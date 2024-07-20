package com.codigoCerto.tarefas.services;

import com.codigoCerto.tarefas.dtos.*;
import com.codigoCerto.tarefas.exceptions.CredentialsInvalidException;
import com.codigoCerto.tarefas.exceptions.EmailAlreadyExistsException;
import com.codigoCerto.tarefas.exceptions.RegisterNotFoundException;
import com.codigoCerto.tarefas.exceptions.ValidationException;
import com.codigoCerto.tarefas.models.User;
import com.codigoCerto.tarefas.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder encoder;

    public User createUserService(UserDTO userDTO){
        if(userDTO.getUsername().length() > 20){
            throw new ValidationException("Tamanho do nome de usuário excedido,deve ter até 20 caracteres");
        }

        if(userDTO.getUsername().isEmpty()){
            throw new ValidationException("Nome do usuário não pode ser nulo");
        }

        if(userDTO.getEmail().length() > 20){
            throw new ValidationException("Tamanho do email excedido,deve ter até 150 caracteres");
        }

        if(userDTO.getEmail().isEmpty()){
            throw new ValidationException("Email não pode ser nulo");
        }

        if(userDTO.getPassword().length() > 100){
            throw new ValidationException("Tamanho da senha excedido,deve ter até 150 caracteres");
        }

        if(userDTO.getPassword().isEmpty()){
            throw new ValidationException("Senha não pode ser nulo");
        }

        boolean existsByEmail = existsByEmailService(userDTO.getEmail());
        if (existsByEmail){
            throw new EmailAlreadyExistsException();
        }

        User userModel = modelMapper.map(userDTO, User.class);
        userModel.setPassword(encoder.encode(userDTO.getPassword()));
        repository.save(userModel);

        return userModel;
    }

    public ResponseUserDTO findUserById(Long id){
        Optional<User> userModel = repository.findById(id);
        if(userModel.isEmpty()){
            throw new RegisterNotFoundException("Usuário não encontrado");
        }
        ResponseUserDTO userDTO = new ResponseUserDTO();
        userDTO.setUsername(userModel.get().getUsername());
        userDTO.setEmail(userModel.get().getEmail());
        return userDTO;
    }

    public Page<UserDTO> findAll(Pageable pageable){
        return repository
                .findAll(pageable)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    public ResponseApiMessageStatus updateUserByIdService(Long id,ResponseUserDTO userDTO){
        if(userDTO.getUsername().length() > 20){
            throw new ValidationException("Tamanho do nome de usuário excedido,deve ter até 20 caracteres");
        }

        if(userDTO.getUsername().isEmpty()){
            throw new ValidationException("Nome do usuário não pode ser nulo");
        }

        if(userDTO.getEmail().length() > 20){
            throw new ValidationException("Tamanho do email excedido,deve ter até 150 caracteres");
        }

        if(userDTO.getEmail().isEmpty()){
            throw new ValidationException("Email não pode ser nulo");
        }

        if (!repository.existsById(id)) {
            throw new RegisterNotFoundException("Usuário não encontrado");
        }

        User existingUser = repository.findById(id).orElseThrow(() ->
                new RegisterNotFoundException("Usuário não encontrado"));


        if (!existingUser.getEmail().equals(userDTO.getEmail()) &&
                repository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(existingUser.getEmail());

        if(userDTO.getEmail() != null){
            existingUser.setEmail(userDTO.getEmail());
        }

        repository.save(existingUser);

        String message = "Usuário atualizado com sucesso";
        Integer status = 200;

        return new ResponseApiMessageStatus(message, status);
    }

    public ResponseApiMessageStatus updatePasswordByIdService(Long id, ResponsePasswordDTO passwordDTO){
        if(passwordDTO.getPassword().length() > 100){
            throw new ValidationException("Tamanho da senha excedido,deve ter até 150 caracteres");
        }

        if(passwordDTO.getPassword().isEmpty()){
            throw new ValidationException("Senha não pode ser nulo");
        }

        boolean existsUser = existsUserById(id);
        if(!existsUser){
            throw new RegisterNotFoundException("Usuário não encontrado");
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
        boolean existsUserId = repository.existsById(id);

        if(!existsUserId){
            throw new RegisterNotFoundException("Usuário não encontrado");
        }

        repository.deleteById(id);
    }

    public Boolean authenticateUser(LoginDTO loginDTO) {
        if(loginDTO.getEmail().length() > 20){
            throw new ValidationException("Tamanho do email excedido,deve ter até 150 caracteres");
        }

        if(loginDTO.getEmail().isEmpty()){
            throw new ValidationException("Email não pode ser nulo");
        }

        if(loginDTO.getPassword().length() > 100){
            throw new ValidationException("Tamanho da senha excedido,deve ter até 150 caracteres");
        }

        if(loginDTO.getPassword().isEmpty()){
            throw new ValidationException("Senha não pode ser nulo");
        }

        Optional<User> optionalUser = repository.findByEmail(loginDTO.getEmail());

        if (optionalUser.isEmpty()) {
            throw new CredentialsInvalidException("Email/Senha incorretos");
        }

        User user = optionalUser.get();

        boolean checkPassword = encoder.matches(loginDTO.getPassword(), user.getPassword());

        if (!checkPassword) {
            throw new CredentialsInvalidException("Senhas não batem");
        }

        return true;
    }

    public ResponseApiMessageStatus updateTokenById(String email,String token){
        User userModel = repository.findByEmail(email).orElseThrow(()-> new RegisterNotFoundException("Usuário não encontrado"));

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
