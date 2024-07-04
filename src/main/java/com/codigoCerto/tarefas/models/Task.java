package com.codigoCerto.tarefas.models;

import com.codigoCerto.tarefas.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_task")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 150,nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    @Enumerated
    private Status status;
    @Column(nullable = false)
    private Long userId;
}
