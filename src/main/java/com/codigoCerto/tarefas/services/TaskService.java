package com.codigoCerto.tarefas.services;

import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.TaskDTO;
import com.codigoCerto.tarefas.models.Task;
import com.codigoCerto.tarefas.repositories.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseApiMessageStatus createTaskService(TaskDTO taskDTO){
        boolean existsUserId=userService.existsUserById(taskDTO.getUserId());
        if (!existsUserId){
            throw new EntityNotFoundException("Usuario não encontrado");
        }

        Task taskModel = modelMapper.map(taskDTO, Task.class);
        repository.save(taskModel);

        String message="Tarefa criada com sucesso";
        Integer status=210;
        return new ResponseApiMessageStatus(message,status);
    }
}
