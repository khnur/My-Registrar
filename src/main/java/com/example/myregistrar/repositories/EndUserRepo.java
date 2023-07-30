package com.example.myregistrar.repositories;

import com.example.myregistrar.models.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EndUserRepo extends JpaRepository<EndUser, Long> {
    Optional<EndUser> findEndUserByUsername(String username);
}
