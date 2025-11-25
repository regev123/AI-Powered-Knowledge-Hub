package com.yashir.knowledgehub.user.repository;

import com.yashir.knowledgehub.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by name
     * @param name the user's name
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<User> findByName(String name);
    
    /**
     * Checks if a user exists with the given name
     * @param name the user's name
     * @return true if user exists, false otherwise
     */
    boolean existsByName(String name);
}

