package com.yashir.knowledgehub.user.service;

import com.yashir.knowledgehub.user.dto.UserCreateRequest;
import com.yashir.knowledgehub.user.dto.UserResponse;
import com.yashir.knowledgehub.user.dto.UserUpdateRoleRequest;

import java.util.List;

/**
 * Service interface for User operations
 */
public interface UserServiceInterface {
    List<UserResponse> getAllUsers();
    UserResponse createUser(UserCreateRequest request);
    UserResponse updateUserRole(UserUpdateRoleRequest request);
}

