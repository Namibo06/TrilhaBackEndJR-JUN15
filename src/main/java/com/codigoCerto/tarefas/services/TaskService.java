package com.codigoCerto.tarefas.services;

import com.codigoCerto.tarefas.controllers.TaskController;
import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.TaskDTO;
import com.codigoCerto.tarefas.exceptions.RegisterNotFoundException;
import com.codigoCerto.tarefas.models.Task;
import com.codigoCerto.tarefas.repositories.TaskRepository;
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

    public Task createTaskService(TaskDTO taskDTO){
        boolean existsUserId=userService.existsUserById(taskDTO.getUserId());
        if (!existsUserId){
            throw new RegisterNotFoundException("Usuário não encontrado");
        }

        Task taskModel = modelMapper.map(taskDTO, Task.class);
        repository.save(taskModel);

        return taskModel;
    }

    public Page<TaskDTO> findAllTasksService(Pageable pageable){
        return repository
                .findAll(pageable)
                .map(task -> modelMapper.map(task,TaskDTO.class));
    }

    public EntityModel<TaskDTO> findTaskByIdService(Long id){
        Task taskModel = repository.findById(id).orElseThrow(()-> new RegisterNotFoundException("Tarefa não encontrada"));
        TaskDTO taskDTO= modelMapper.map(taskModel, TaskDTO.class);

        return EntityModel.of(taskDTO,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskController.class).findTaskById(id)).withSelfRel());
    }

    public ResponseApiMessageStatus updateTaskByIdService(Long id,TaskDTO taskDTO){
        boolean existsUserId=userService.existsUserById(taskDTO.getUserId());

        if (!existsUserId){
            throw new RegisterNotFoundException("Usuário não encontrado");
        }

        Task taskModel = repository.findById(id).orElseThrow(() -> new RegisterNotFoundException("Tarefa não encontrada"));
        taskModel.setTitle(taskDTO.getTitle());
        taskModel.setDescription(taskDTO.getDescription());
        taskModel.setStatus(taskDTO.getStatus());
        repository.save(taskModel);

        String message="Tarefa atualizada com sucesso";
        Integer status=200;
        return new ResponseApiMessageStatus(message,status);
    }

    public void deleteTaskByIdService(Long id){
        boolean existsTaskId = repository.existsById(id);

        if(!existsTaskId){
            throw new RegisterNotFoundException("Tarefa não encontrada");
        }

        repository.deleteById(id);
    }
}
