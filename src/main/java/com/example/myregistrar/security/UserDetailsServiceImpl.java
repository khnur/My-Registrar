package com.example.myregistrar.security;

import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.models.Student;
import com.example.myregistrar.repositories.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final StudentRepo studentRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepo.findStudentByEmail(username)
                .orElseThrow(() -> new StudentNotFoundException("There is no student with such username=" + username));

        return new User(
                student.getEmail(),
                student.getPassword(),
                student.isActive(),
                student.isActive(),
                student.isActive(),
                student.isActive(),
                List.of(new SimpleGrantedAuthority(student.getRole()))
        );
    }
}
