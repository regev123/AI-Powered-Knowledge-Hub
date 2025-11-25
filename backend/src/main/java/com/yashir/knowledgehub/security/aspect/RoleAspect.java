package com.yashir.knowledgehub.security.aspect;

import com.yashir.knowledgehub.security.annotation.RequireRole;
import com.yashir.knowledgehub.security.exception.UnauthorizedException;
import com.yashir.knowledgehub.security.service.RequestContextService;
import com.yashir.knowledgehub.security.service.RoleValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

/**
 * AOP Aspect for role-based access control
 * Intercepts methods annotated with @RequireRole and checks user role from header
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RoleAspect {

    private final RequestContextService requestContextService;
    private final RoleValidationService roleValidationService;

    /**
     * Intercepts methods annotated with @RequireRole
     * Checks if the user role from header matches the required role
     * @param joinPoint the join point (method being intercepted)
     * @param requireRole the annotation with required role
     * @throws UnauthorizedException if user role doesn't match required role
     */
    @Before("@annotation(requireRole)")
    public void checkRole(JoinPoint joinPoint, RequireRole requireRole) {
        HttpServletRequest request = requestContextService.getCurrentRequest();
        
        if (request == null) {
            log.warn("Cannot check role: Request context not available");
            throw new UnauthorizedException("Request context not available");
        }

        // Skip CORS preflight OPTIONS requests (they don't carry custom headers)
        if (requestContextService.isPreflightRequest(request)) {
            return;
        }

        String userRoleHeader = request.getHeader(roleValidationService.getUserRoleHeaderName());
        roleValidationService.validateRole(userRoleHeader, requireRole.value());
    }
}

