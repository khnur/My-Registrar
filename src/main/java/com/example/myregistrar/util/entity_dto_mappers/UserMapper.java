package com.example.myregistrar.util.entity_dto_mappers;

import com.example.myregistrar.dtos.auth_dto.UserDto;
import com.example.myregistrar.models.EndUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto userToUserDto(EndUser endUser);
    EndUser userDtoToUser(UserDto userDto);
}
