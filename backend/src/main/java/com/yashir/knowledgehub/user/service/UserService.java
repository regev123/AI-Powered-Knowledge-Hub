package com.yashir.knowledgehub.user.service;

import com.yashir.knowledgehub.user.exception.UserAlreadyExistsException;
import com.yashir.knowledgehub.user.exception.UserNotFoundException;
import com.yashir.knowledgehub.user.dto.UserCreateRequest;
import com.yashir.knowledgehub.user.dto.UserResponse;
import com.yashir.knowledgehub.user.dto.UserUpdateRoleRequest;
import com.yashir.knowledgehub.user.mapper.UserMapperInterface;
import com.yashir.knowledgehub.user.model.User;
import com.yashir.knowledgehub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for User operations
 * Handles only user business logic
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final UserMapperInterface userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        validateUserDoesNotExist(request.getName());
        
        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
    
    @Override
    @Transactional
    public UserResponse updateUserRole(UserUpdateRoleRequest request) {
        User user = findUserById(request.getId());
        user.setRole(request.getRole());
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    /**
     * Validates that a user with the given name does not already exist
     * @param userName the user name to validate
     * @throws UserAlreadyExistsException if a user with the name already exists
    */
    private void validateUserDoesNotExist(String userName) {
        if (userRepository.existsByName(userName)) {
            throw new UserAlreadyExistsException(userName);
        }
    }

    /**
     * Finds a user by ID or throws exception if not found
     * Encapsulates the logic for finding a user by ID
     * @param id the user ID
     * @return the found user
     * @throws UserNotFoundException if user is not found
     */
    private User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
}

