package com.yashir.knowledgehub.security.annotation;

import com.yashir.knowledgehub.user.model.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods that require specific user roles
 * Used with AOP aspect for centralized permission checking
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {
    
    /**
     * The required role for accessing the annotated method
     * @return the required UserRole
     */
    UserRole value();
}

