
package com.todo.service.criteria;

import com.todo.service.dto.TarefaDTO;
import com.todo.service.dto.UserDTO;

/**
 *
 * @author felipe
 */
public class IdAddTarefaStrategy implements IdAddStrategy{

    
    private TarefaDTO dto;
    
    public IdAddTarefaStrategy( TarefaDTO dto){
        this.dto = dto;
    }
    
    @Override
    public void addId(UserDTO userDTO) {
       // this.dto
       
       this.dto.setUser(userDTO);

    }

    
}
