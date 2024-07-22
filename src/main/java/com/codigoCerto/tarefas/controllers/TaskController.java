package com.codigoCerto.tarefas.controllers;

import com.codigoCerto.tarefas.dtos.ResponseApiMessageStatus;
import com.codigoCerto.tarefas.dtos.TaskDTO;
import com.codigoCerto.tarefas.models.Task;
import com.codigoCerto.tarefas.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "Criar Tarefa", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ResponseApiMessageStatus> createTask(@RequestBody @Valid TaskDTO taskDTO, UriComponentsBuilder uriBuilder){
        Task task = service.createTaskService(taskDTO);
        Long taskId = task.getId();
        URI path = uriBuilder.path("/tasks/{id}").buildAndExpand(taskId).toUri();

        String message="Tarefa criada com sucesso";
        Integer status=201;
        ResponseApiMessageStatus response = new ResponseApiMessageStatus(message,status);

        return ResponseEntity.created(path).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar todas tarefas", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Page<TaskDTO>> findAllTasks(@PageableDefault(size = 15) Pageable pageable){
        Page<TaskDTO> pageableTasks =  service.findAllTasksService(pageable);

        return ResponseEntity.ok(pageableTasks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter uma tarefa", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<EntityModel<TaskDTO>> findTaskById(@PathVariable Long id) {
        EntityModel<TaskDTO> entityModel = service.findTaskByIdService(id);

        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma tarefa", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ResponseApiMessageStatus> updateTaskById(@PathVariable Long id,@RequestBody TaskDTO taskDTO){
        ResponseApiMessageStatus response = service.updateTaskByIdService(id,taskDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma tarefa", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id){
        service.deleteTaskByIdService(id);

        return ResponseEntity.noContent().build();
    }
}
