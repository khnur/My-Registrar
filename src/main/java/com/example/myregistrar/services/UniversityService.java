package com.example.myregistrar.services;

import com.example.myregistrar.models.University;

import java.util.List;

public interface UniversityService {
    void createUniversity(University university);

    University getUniversityById(Long id);

    List<University> getAllUniversities();

    List<University> getUniversitiesByName(String name);

    University getUniversityByNameAndCountry(String name, String country);
}
