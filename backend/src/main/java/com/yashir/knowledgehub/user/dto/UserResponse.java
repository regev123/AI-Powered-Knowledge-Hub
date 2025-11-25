package com.yashir.knowledgehub.user.dto;

import com.yashir.knowledgehub.user.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user response data
 * Used for returning user information in API responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private UserRole role;
}

