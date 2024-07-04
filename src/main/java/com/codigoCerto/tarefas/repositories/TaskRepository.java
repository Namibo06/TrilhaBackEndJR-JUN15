package com.codigoCerto.tarefas.repositories;

import com.codigoCerto.tarefas.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
}
