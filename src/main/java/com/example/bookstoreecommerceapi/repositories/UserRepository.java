package com.example.bookstoreecommerceapi.repositories;

import com.example.bookstoreecommerceapi.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
