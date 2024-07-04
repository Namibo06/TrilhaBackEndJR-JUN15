package com.codigoCerto.tarefas.controllers;

import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.TaskDTO;
import com.codigoCerto.tarefas.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService service;

    @PostMapping
    public ResponseEntity<ResponseApiMessageStatus> createTask(@RequestBody @Valid TaskDTO taskDTO, UriComponentsBuilder uriBuilder){
        ResponseApiMessageStatus response = service.createTaskService(taskDTO);
        URI path = uriBuilder.path("/tasks/{id}").buildAndExpand(taskDTO.getId()).toUri();

        return ResponseEntity.created(path).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TaskDTO>> findAllTasks(@PageableDefault(size = 15) Pageable pageable){
        Page<TaskDTO> pageableTasks =  service.findAllTasksService(pageable);

        return ResponseEntity.ok(pageableTasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TaskDTO>> findById(@PathVariable Long id) {
        EntityModel<TaskDTO> entityModel = service.findTaskByIdService(id);

        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseApiMessageStatus> updateTaskById(@PathVariable Long id,@RequestBody TaskDTO taskDTO){
        ResponseApiMessageStatus response = service.updateTaskByIdService(id,taskDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id){
        service.deleteTaskByIdService(id);

        return ResponseEntity.noContent().build();
    }
}
