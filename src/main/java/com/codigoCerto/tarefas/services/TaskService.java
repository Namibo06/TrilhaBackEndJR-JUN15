package com.codigoCerto.tarefas.services;

import com.codigoCerto.tarefas.controllers.TaskController;
import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.TaskDTO;
import com.codigoCerto.tarefas.dtos.UserDTO;
import com.codigoCerto.tarefas.models.Task;
import com.codigoCerto.tarefas.repositories.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
        Integer status=201;
        return new ResponseApiMessageStatus(message,status);
    }

    public Page<TaskDTO> findAllTasksService(Pageable pageable){
        return repository
                .findAll(pageable)
                .map(task -> modelMapper.map(task,TaskDTO.class));
    }

    public EntityModel<TaskDTO> findTaskByIdService(Long id){
        Task taskModel = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Tarefa não encontrada"));
        TaskDTO taskDTO= modelMapper.map(taskModel, TaskDTO.class);

        return EntityModel.of(taskDTO,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskController.class).findTaskById(id)).withSelfRel());
    }

    public ResponseApiMessageStatus updateTaskByIdService(Long id,TaskDTO taskDTO){
        boolean existsUserId=userService.existsUserById(taskDTO.getUserId());

        if (!existsUserId){
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        Task taskModel = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
        taskModel.setTitle(taskDTO.getTitle());
        taskModel.setDescription(taskDTO.getDescription());
        taskModel.setStatus(taskDTO.getStatus());
        repository.save(taskModel);

        String message="Tarefa atualizada com sucesso";
        Integer status=200;
        return new ResponseApiMessageStatus(message,status);
    }

    public void deleteTaskByIdService(Long id){
        repository.deleteById(id);
    }
}
