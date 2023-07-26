package com.example.myregistrar.util.entity_dto_mappers;

import com.example.myregistrar.dtos.UniversityDto;
import com.example.myregistrar.models.University;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UniversityMapper {
    UniversityMapper INSTANCE = Mappers.getMapper(UniversityMapper.class);

    UniversityDto universityToUniversityDto(University university);

    University universityDtoToUniversity(UniversityDto universityDto);

    List<UniversityDto> universityListToUniversityDtoList(List<University> universityList);
    List<University> universityDtoListToUniversityList(List<UniversityDto> universityDtoList);
}
