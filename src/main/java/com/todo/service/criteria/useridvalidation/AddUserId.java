package com.todo.service.criteria.useridvalidation;

import com.todo.domain.Authority;
import com.todo.domain.User;
import com.todo.repository.UserRepository;
import com.todo.security.AuthoritiesConstants;
import com.todo.service.mapper.UserMapper;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LongFilter;
import com.todo.service.dto.UserDTO;
import java.lang.reflect.Method;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author felipe
 */
@Repository
public class AddUserId {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AddUserId(UserMapper userMapper, UserRepository userRepository) {

        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }
    
    
    @Transactional
    public boolean checkOwnerDTOId(Long userDTOId) throws Exception {
        User user = getCurrentUser();
        Set<Authority> authories = user.getAuthorities();
        for (Authority aut : authories) {
            if (aut.getName().equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {
                return true;//admin can do everything.  
            }
        }
        if(user.getId().compareTo(userDTOId) == 0)
            return true;
        return false;
    }
    

    @Transactional
    public void setUserOwnerIDFilter(Criteria criteria , Class clazz) throws Exception {

        User user = getCurrentUser();
        Set<Authority> authories = user.getAuthorities();
        for (Authority aut : authories) {
            if (aut.getName().equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {
                return;
            }
        }
        LongFilter filter = new LongFilter();
        filter.setEquals(user.getId());
        Method sumInstanceMethod =  clazz.getMethod("setUserId", LongFilter.class);
        sumInstanceMethod.invoke(clazz.cast(criteria), filter);
    }

    @Transactional
    private User getCurrentUser() throws Exception {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        Optional<User> user = userRepository.findOneByLogin(username);
        if (user.isEmpty()) {
            throw new Exception("user not found " + username);
        }
        return user.get();
    }

    @Transactional
    public void setDTOUserId(AddIdStrategy strategy) throws Exception {
        User user = getCurrentUser();
        UserDTO userDTO = userMapper.userToUserDTO(user);
        strategy.addUserId(userDTO);
    }
}
