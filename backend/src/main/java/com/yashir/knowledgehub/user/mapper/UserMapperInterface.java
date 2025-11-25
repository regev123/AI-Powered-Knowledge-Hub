package com.yashir.knowledgehub.user.mapper;

import com.yashir.knowledgehub.mapper.DtoToEntityMapper;
import com.yashir.knowledgehub.mapper.EntityToDtoMapper;
import com.yashir.knowledgehub.user.dto.UserCreateRequest;
import com.yashir.knowledgehub.user.dto.UserResponse;
import com.yashir.knowledgehub.user.model.User;

/**
 * User mapper interface
 * Extends base mapper interfaces following Interface Segregation Principle
 */
public interface UserMapperInterface extends 
        EntityToDtoMapper<User, UserResponse>,
        DtoToEntityMapper<UserCreateRequest, User> {
}

