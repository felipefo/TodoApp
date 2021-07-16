package com.todo.repository;

import com.todo.domain.Tarefa;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tarefa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>, JpaSpecificationExecutor<Tarefa> {
    @Query("select tarefa from Tarefa tarefa where tarefa.user.login = ?#{principal.username}")
    List<Tarefa> findByUserIsCurrentUser();
}
