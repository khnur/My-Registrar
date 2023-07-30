package com.example.myregistrar.services.service_impls;

import com.example.myregistrar.models.EndUser;
import com.example.myregistrar.models.model_utils.StudentEnrolmentManager;
import com.example.myregistrar.repositories.EndUserRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EndUserServiceImplTest {
    @Mock
    EndUserRepo endUserRepo;
    @InjectMocks
    EndUserServiceImpl endUserServiceImpl;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    StudentEnrolmentManager studentEnrolmentManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        EndUser user = new EndUser("username", "password", "role");
        when(endUserRepo.save(any())).thenReturn(user);

        EndUser result = endUserServiceImpl.createUser(user);

        Assertions.assertEquals(user, result);
    }

    @Test
    @Transactional
    void testCreateAndRetrieveUser() {
        EndUser newUser = new EndUser("username", "password", "ROLE_USER");

        when(endUserRepo.save(any())).thenReturn(newUser);
        when(endUserRepo.findEndUserByUsername("username")).thenReturn(Optional.of(newUser));

        endUserServiceImpl.createUser(newUser);

        EndUser retrievedUser = endUserServiceImpl.getUserByUsername("username");

        assertNotNull(retrievedUser);
        assertEquals(newUser.getUsername(), retrievedUser.getUsername());
        assertEquals(newUser.getPassword(), retrievedUser.getPassword());
        assertEquals(newUser.getRole(), retrievedUser.getRole());
    }

    @Test
    void testGetUserByUsername() {
        EndUser user = new EndUser("username", "password", "role");
        when(endUserRepo.findEndUserByUsername(anyString())).thenReturn(Optional.of(user));

        EndUser result = endUserServiceImpl.getUserByUsername("username");
        Assertions.assertEquals(user.getUsername(), result.getUsername());
    }
}