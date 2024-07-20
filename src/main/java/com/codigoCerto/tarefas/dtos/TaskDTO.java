package com.codigoCerto.tarefas.dtos;

import com.codigoCerto.tarefas.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class TaskDTO {
    @NonNull
    @NotBlank
    private String title;
    private String description;
    @NonNull
    private Status status;
    @NonNull
    private Long userId;
}
