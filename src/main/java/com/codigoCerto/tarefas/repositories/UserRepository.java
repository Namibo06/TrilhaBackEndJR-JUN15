package com.codigoCerto.tarefas.repositories;

import com.codigoCerto.tarefas.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
