package com.example.myregistrar.controllers;

import com.example.myregistrar.dtos.UniversityDto;
import com.example.myregistrar.services.UniversityService;
import com.example.myregistrar.util.entity_dto_mappers.UniversityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping
    public List<UniversityDto> getAllUniversities() {
        return UniversityMapper.INSTANCE
                .universityListToUniversityDtoList(universityService.getAllUniversities());
    }
}
