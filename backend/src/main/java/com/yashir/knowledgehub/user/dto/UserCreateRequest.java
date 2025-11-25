package com.yashir.knowledgehub.user.dto;

import com.yashir.knowledgehub.user.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for creating a new user
 * Contains validation annotations for input validation
 */
@Data
public class UserCreateRequest {
    
    @NotBlank(message = "User name is required")
    @Size(min = 2, max = 50, message = "User name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "User role is required")
    private UserRole role;
}

