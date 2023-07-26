package com.example.myregistrar.repositories;

import com.example.myregistrar.models.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepo extends JpaRepository<University, Long> {
    List<University> findUniversitiesByName(String name);

    Optional<University> findUniversityByNameAndCountry(String name, String country);
}
