package com.yashir.knowledgehub.security.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Service for retrieving HTTP request context
 */
@Service
@Slf4j
public class RequestContextService {

    /**
     * Gets the current HTTP request from the request context
     * @return the HttpServletRequest, or null if not available
     */
    public HttpServletRequest getCurrentRequest() {
        try {
            ServletRequestAttributes attributes = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attributes != null ? attributes.getRequest() : null;
        } catch (Exception e) {
            log.warn("Failed to get current request: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Checks if the current request is a CORS preflight OPTIONS request
     * @param request the HTTP request
     * @return true if it's a preflight request, false otherwise
     */
    public boolean isPreflightRequest(HttpServletRequest request) {
        return request != null && "OPTIONS".equalsIgnoreCase(request.getMethod());
    }
}

