package com.yashir.knowledgehub.user.mapper;

import com.yashir.knowledgehub.user.dto.UserCreateRequest;
import com.yashir.knowledgehub.user.dto.UserResponse;
import com.yashir.knowledgehub.user.model.User;
import org.springframework.stereotype.Component;

/**
 * Mapper implementation for User entity and DTOs
 * handles only User mapping
 */
@Component
public class UserMapper implements UserMapperInterface {

    @Override
    public UserResponse toDto(User entity) {
        UserResponse response = new UserResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setRole(entity.getRole());
        return response;
    }

    @Override
    public User toEntity(UserCreateRequest dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setRole(dto.getRole());
        return user;
    }
}

