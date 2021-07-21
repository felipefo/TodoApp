
package com.todo.service.criteria.useridvalidation;

import com.todo.service.dto.UserDTO;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felipe
 */
public class AddIddTarefaStrategy implements AddIdStrategy{
    private Object dto;
    private Class clazz;
    
    public AddIddTarefaStrategy( Object dto, Class clazz){
        this.dto = dto;
        this.clazz = clazz;
    }
    
    @Override
    public void addUserId(UserDTO userDTO) {
        try {
            Method sumInstanceMethod =  clazz.getMethod("setUser", UserDTO.class); 
            sumInstanceMethod.invoke(clazz.cast(this.dto), userDTO);
        } catch (Exception ex) {
            Logger.getLogger(AddIddTarefaStrategy.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    
}
