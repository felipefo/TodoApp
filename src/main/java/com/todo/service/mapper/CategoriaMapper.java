package com.todo.service.mapper;

import com.todo.domain.*;
import com.todo.service.dto.CategoriaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categoria} and its DTO {@link CategoriaDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, TarefaMapper.class })
public interface CategoriaMapper extends EntityMapper<CategoriaDTO, Categoria> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    CategoriaDTO toDto(Categoria s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoriaDTO toDtoId(Categoria categoria);
}
