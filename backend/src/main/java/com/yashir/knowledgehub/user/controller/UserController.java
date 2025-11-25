package com.yashir.knowledgehub.user.controller;

import com.yashir.knowledgehub.security.annotation.RequireRole;
import com.yashir.knowledgehub.user.dto.UserCreateRequest;
import com.yashir.knowledgehub.user.dto.UserResponse;
import com.yashir.knowledgehub.user.dto.UserUpdateRoleRequest;
import com.yashir.knowledgehub.user.model.UserRole;
import com.yashir.knowledgehub.user.service.UserServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for User operations
 * Handles only HTTP concerns and delegates business logic to service layer
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceInterface userService;

    /**
     * Retrieves all users
     * Note: No role restriction - needed for initial user list loading
     * Frontend handles UI restrictions for non-admin users
     * @return ResponseEntity containing list of all users
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Creates a new user
     * Requires ADMIN role
     * @param request the user creation request with validation
     * @return ResponseEntity containing the created user with HTTP 201 status
     */
    @PostMapping
    @RequireRole(UserRole.ADMIN)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates a user's role
     * Requires ADMIN role
     * @param request the role update request with validation
     * @return ResponseEntity containing the updated user
     */
    @PutMapping("/role")
    @RequireRole(UserRole.ADMIN)
    public ResponseEntity<UserResponse> updateUserRole(
            @Valid @RequestBody UserUpdateRoleRequest request) {
        UserResponse response = userService.updateUserRole(request);
        return ResponseEntity.ok(response);
    }
}

