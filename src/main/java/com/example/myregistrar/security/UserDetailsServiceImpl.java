package com.example.myregistrar.security;

import com.example.myregistrar.models.EndUser;
import com.example.myregistrar.services.EndUserService;
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
    private final EndUserService endUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EndUser user = endUserService.getUserByUsername(username);

        return new User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
