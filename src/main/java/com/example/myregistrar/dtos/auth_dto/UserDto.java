package com.example.myregistrar.dtos.auth_dto;

import com.example.myregistrar.annotation.ValidRole;
import com.example.myregistrar.util.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank(message = "username is required")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password must be at least 8 characters long")
    private String password;

    @ValidRole
    private String role = Role.USER.getRoleName();

    private LocalDateTime registeredAt = LocalDateTime.now();

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
