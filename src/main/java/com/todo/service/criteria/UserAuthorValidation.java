package com.todo.service.criteria;

import com.todo.domain.Authority;
import com.todo.domain.User;
import com.todo.repository.UserRepository;
import com.todo.security.AuthoritiesConstants;
import com.todo.service.dto.TarefaDTO;
import com.todo.service.mapper.UserMapper;
import java.security.InvalidAlgorithmParameterException;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.LongFilter;
import com.todo.service.dto.UserDTO;
import com.todo.web.rest.errors.BadRequestAlertException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author felipe
 */
@Repository

public class UserAuthorValidation {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserAuthorValidation(UserMapper userMapper, UserRepository userRepository) {

        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public void setUserOwnerIDFilter(Criteria criteria) throws InvalidAlgorithmParameterException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        User user = getCurrentUser();
        Set<Authority> authories = user.getAuthorities();
        for (Authority aut : authories) {
            if (aut.getName().equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {
                return;
            }
        }
        LongFilter filter = new LongFilter();
        filter.setEquals(user.getId());
        Method sumInstanceMethod = Criteria.class.getMethod("setId", LongFilter.class);
        sumInstanceMethod.invoke(criteria, filter);
    }

    @Transactional
    private User getCurrentUser() throws BadRequestAlertException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        Optional<User> user = userRepository.findOneByLogin(username);
        if (user.isEmpty()) {
            throw new BadRequestAlertException("user not found ", "USER", "user not found");
        }
        return user.get();
    }

    @Transactional
    public void setDTOUserId(IdAddTarefaStrategy strategy) throws BadRequestAlertException {
        User user = getCurrentUser();
        UserDTO userDTO = userMapper.userToUserDTO(user);
        strategy.addId(userDTO);
    }
}
