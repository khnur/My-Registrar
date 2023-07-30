package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.auth_dto.JwtDto;
import com.example.myregistrar.dtos.auth_dto.ResponseDto;
import com.example.myregistrar.dtos.auth_dto.UserDto;
import com.example.myregistrar.security.JwtService;
import com.example.myregistrar.services.EndUserService;
import com.example.myregistrar.util.entity_dto_mappers.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final EndUserService endUserService;

    @PostMapping("/login")
    public ResponseDto<JwtDto> auth(@RequestBody UserDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());

        if (userDetails == null) {
            return new ResponseDto<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Something went wrong",
                    null
            );
        }
        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "Jwt is created for the user: " + userDto.getUsername(),
                new JwtDto(jwtService.generateToken(userDetails))
        );
    }

    @PostMapping("/register")
    public ResponseDto<UserDto> register(@RequestBody @Valid UserDto userDto) {
        UserDto newUser = UserMapper.INSTANCE.userToUserDto(
                endUserService.createUser(
                        UserMapper.INSTANCE.userDtoToUser(userDto)
                )
        );

        if (newUser == null) {
            return new ResponseDto<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Something went wrong",
                    null
            );
        }

        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "new user created with username=" + newUser.getUsername() + " and role=" + newUser.getRole(),
                newUser
        );
    }
}
