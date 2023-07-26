package com.example.myregistrar.dtos;

import com.example.myregistrar.util.JsonMapper;
import lombok.Data;

@Data
public class UniversityDto {
    private Long id;

    private String name;

    private String country;

    private String city;

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }
}
