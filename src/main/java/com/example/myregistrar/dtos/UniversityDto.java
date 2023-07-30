package com.example.myregistrar.dtos;

import com.example.myregistrar.models.University;
import com.example.myregistrar.util.JsonMapper;
import com.example.myregistrar.util.entity_dto_mappers.UniversityMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDto {
    private Long id;

    @NotBlank
    private String name;

    private String country;

    private String city;

    public University toUniversity() {
        return UniversityMapper.INSTANCE.universityDtoToUniversity(this);
    }

    public String toJson() {
        return JsonMapper.toJsonString(this);
    }
}
