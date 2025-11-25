package com.yashir.knowledgehub.user.dto;

import com.yashir.knowledgehub.user.model.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for updating a user's role
 * Contains validation annotations for input validation
 */
@Data
public class UserUpdateRoleRequest {
    
    @NotNull(message = "User id is required")
    private Long id;

    @NotNull(message = "User role is required")
    private UserRole role;
}

