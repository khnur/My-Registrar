package com.example.myregistrar.dtos.auth_dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseDto<T> {
    private int status;
    private String message;
    private LocalDateTime localDateTime;
    private T object;

    public ResponseDto(int status, String message, T object) {
        this.status = status;
        this.message = message;
        this.localDateTime = LocalDateTime.now();
        this.object = object;
    }
}
