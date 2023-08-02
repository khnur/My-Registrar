package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.exceptions.conflict.UniversityAlreadyExistsException;
import com.example.myregistrar.exceptions.not_found.UniversityNotFoundException;
import com.example.myregistrar.models.University;
import com.example.myregistrar.repositories.UniversityRepo;
import com.example.myregistrar.services.UniversityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepo universityRepo;

    @Transactional
    @Override
    public University createUniversity(University university) {
        if (university == null) {
            throw new UniversityNotFoundException("Provided university null");
        } else if (university.getId() != null) {
            throw new UniversityAlreadyExistsException("University with id=" + university.getId() + " already exists");
        }
        return universityRepo.save(university);
    }

    @Override
    public University getUniversityById(Long id) {
        return universityRepo.findById(id)
                .orElseThrow(() -> new UniversityNotFoundException("University with id=" + id + " NOT FOUND"));
    }

    @Override
    public List<University> getAllUniversities() {
        List<University> universities = universityRepo.findAll();
        if (universities.isEmpty()) {
            throw new UniversityNotFoundException("There is no university");
        }
        return universities;
    }

    @Override
    public List<University> getUniversitiesByName(String name) {
        List<University> universities = universityRepo.findUniversitiesByName(name);
        if (universities.isEmpty()) {
            throw new UniversityNotFoundException("There is no university with name=" + name);
        }
        return universities;
    }

    @Override
    public University getUniversityByNameAndCountry(String name, String country) {
        return universityRepo.findUniversityByNameAndCountry(name, country)
                .orElseThrow(() -> new UniversityNotFoundException("There is no university with name=" + name + " and country=" + country));
    }
}
