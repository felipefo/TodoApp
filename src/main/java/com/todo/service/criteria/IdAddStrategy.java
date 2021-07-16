/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.todo.service.criteria;

import com.todo.service.dto.UserDTO;

/**
 *
 * @author felipe
 */
public interface IdAddStrategy {
    
    
    public void addId(UserDTO userDTO);
    
}
