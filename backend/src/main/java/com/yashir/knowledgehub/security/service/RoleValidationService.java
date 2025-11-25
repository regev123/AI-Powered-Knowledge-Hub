package com.yashir.knowledgehub.security.service;

import com.yashir.knowledgehub.security.exception.UnauthorizedException;
import com.yashir.knowledgehub.user.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for validating user roles
 */
@Service
@Slf4j
public class RoleValidationService {

    private static final String USER_ROLE_HEADER = "X-User-Role";

    /**
     * Validates if the user role from header matches the required role
     * @param userRoleHeader the role value from HTTP header
     * @param requiredRole the required role for access
     * @throws UnauthorizedException if validation fails
     */
    public void validateRole(String userRoleHeader, UserRole requiredRole) {
        if (userRoleHeader == null || userRoleHeader.isEmpty()) {
            log.warn("User role header '{}' is missing", USER_ROLE_HEADER);
            throw new UnauthorizedException("User role is required but not provided");
        }

        try {
            UserRole userRole = UserRole.valueOf(userRoleHeader.toUpperCase());
            
            if (userRole != requiredRole) {
                log.warn("Access denied: Required role '{}', but user has role '{}'", requiredRole, userRole);
                throw new UnauthorizedException(
                    String.format("Access denied. Required role: %s, but user has role: %s", 
                        requiredRole, userRole)
                );
            }
        } catch (IllegalArgumentException e) {
            log.warn("Invalid user role in header: '{}'", userRoleHeader);
            throw new UnauthorizedException("Invalid user role: " + userRoleHeader);
        }
    }

    /**
     * Gets the user role header name
     * @return the header name
     */
    public String getUserRoleHeaderName() {
        return USER_ROLE_HEADER;
    }
}

