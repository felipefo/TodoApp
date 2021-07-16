package com.todo.repository;

import com.todo.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Categoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, JpaSpecificationExecutor<Categoria> {
    @Query("select categoria from Categoria categoria where categoria.user.login = ?#{principal.username}")
    List<Categoria> findByUserIsCurrentUser();
}
