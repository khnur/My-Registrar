package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.exceptions.UniversityAlreadyExistsException;
import com.example.myregistrar.exceptions.UniversityNotFoundException;
import com.example.myregistrar.models.University;
import com.example.myregistrar.repositories.UniversityRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversityServiceImplTest {
    @Mock
    UniversityRepo universityRepo;
    @InjectMocks
    UniversityServiceImpl universityServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUniversity() {
        University university = new University("name", "country", "city");
        when(universityRepo.save(any())).thenReturn(university);

        University result = universityServiceImpl.createUniversity(university);
        assertEquals(university, result);

        University existingUniversity = new University("existing_name", "existing_country", "existing_city");
        existingUniversity.setId(1L);
        assertThrows(UniversityAlreadyExistsException.class, () -> universityServiceImpl.createUniversity(existingUniversity));

        assertThrows(UniversityNotFoundException.class, () -> universityServiceImpl.createUniversity(null));
    }

    @Test
    void testGetUniversityById_UniversityFound() {
        Long universityId = 1L;
        University mockUniversity = new University("name", "country", "city");
        when(universityRepo.findById(anyLong())).thenReturn(Optional.of(mockUniversity));

        University result = universityServiceImpl.getUniversityById(universityId);

        assertEquals(mockUniversity, result);
    }

    @Test
    void testGetUniversityById_UniversityNotFound() {
        when(universityRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UniversityNotFoundException.class, () -> universityServiceImpl.getUniversityById(1L));
    }

    @Test
    void testGetAllUniversities_UniversitiesFound() {
        List<University> mockUniversities = List.of(new University("name", "country", "city"));
        when(universityRepo.findAll()).thenReturn(mockUniversities);

        List<University> result = universityServiceImpl.getAllUniversities();

        assertEquals(mockUniversities, result);
    }

    @Test
    void testGetAllUniversities_NoUniversitiesFound() {
        when(universityRepo.findAll()).thenReturn(Collections.emptyList());
        assertThrows(UniversityNotFoundException.class, () -> universityServiceImpl.getAllUniversities());
    }

    @Test
    void testGetUniversitiesByName() {
        when(universityRepo.findUniversitiesByName(anyString())).thenReturn(List.of(new University("name", "country", "city")));

        List<University> result = universityServiceImpl.getUniversitiesByName("name");
        assertEquals(List.of(new University("name", "country", "city")), result);
    }

    @Test
    void testGetUniversityByNameAndCountry_UniversityFound() {
        String name = "name";
        String country = "country";
        University mockUniversity = new University(name, country, "city");
        when(universityRepo.findUniversityByNameAndCountry(name, country)).thenReturn(Optional.of(mockUniversity));

        University result = universityServiceImpl.getUniversityByNameAndCountry(name, country);

        assertEquals(mockUniversity, result);
    }

    @Test
    void testGetUniversityByNameAndCountry_UniversityNotFound() {
        when(universityRepo.findUniversityByNameAndCountry(anyString(), anyString())).thenReturn(Optional.empty());
        assertThrows(UniversityNotFoundException.class, () -> universityServiceImpl.getUniversityByNameAndCountry("name", "country"));
    }
}