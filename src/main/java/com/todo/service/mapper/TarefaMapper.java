package com.todo.service.mapper;

import com.todo.domain.*;
import com.todo.service.dto.TarefaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tarefa} and its DTO {@link TarefaDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, CategoriaMapper.class })
public interface TarefaMapper extends EntityMapper<TarefaDTO, Tarefa> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
   //restringindo somente o id no retorno 
   //@Mapping(target = "categoria", source = "categoria", qualifiedByName = "id")
            
  
         
            
    TarefaDTO toDto(Tarefa s);
}
