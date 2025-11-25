package com.yashir.knowledgehub.user.config;

import com.yashir.knowledgehub.user.model.User;
import com.yashir.knowledgehub.user.model.UserRole;
import com.yashir.knowledgehub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Configuration class for initializing default admin user on application startup
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminUserInitializer {

    private static final String DEFAULT_ADMIN_NAME = "Admin";
    private final UserRepository userRepository;

    /**
     * Initializes a default admin user if no admin user exists
     * Runs after the application context is fully loaded
     */
    @Bean
    @Transactional
    public ApplicationRunner initializeAdminUser() {
        return args -> {
            // Check if any admin user exists
            List<User> allUsers = userRepository.findAll();
            boolean adminExists = allUsers.stream()
                    .anyMatch(user -> user.getRole() == UserRole.ADMIN);

            if (!adminExists) {
                // Check if default admin name already exists (with different role)
                if (!userRepository.existsByName(DEFAULT_ADMIN_NAME)) {
                    User adminUser = new User();
                    adminUser.setName(DEFAULT_ADMIN_NAME);
                    adminUser.setRole(UserRole.ADMIN);
                    userRepository.save(adminUser);
                } else {
                    // If user exists but is not admin, update role to ADMIN
                    User existingUser = userRepository.findByName(DEFAULT_ADMIN_NAME)
                            .orElseThrow(() -> new IllegalStateException("User exists but not found"));
                    existingUser.setRole(UserRole.ADMIN);
                    userRepository.save(existingUser);
                }
            }
        };
    }
}

