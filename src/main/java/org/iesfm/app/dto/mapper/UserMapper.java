package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.RoleDto;
import org.iesfm.app.dto.UserDto;
import org.iesfm.app.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserDto userDto) {

        UserEntity user = new UserEntity();

        user.setName(userDto.getName());
        user.setFirstSurname(userDto.getFirstSurname());
        user.setSecondSurname(userDto.getSecondSurname());
        user.setDateCre(userDto.getDateCre());
        user.setDateMod(userDto.getDateMod());
        user.setEmail(userDto.getEmail());
        user.setPass(userDto.getPass());
        user.setRole(RoleMapper.toEnity(userDto.getRole()));
        return user;
    }

    public static UserDto toDto(UserEntity entity) {

        UserDto dto = new UserDto();

        dto.setName(entity.getName());
        dto.setFirstSurname(entity.getFirstSurname());
        dto.setSecondSurname(entity.getSecondSurname());
        dto.setDateCre(entity.getDateCre());
        dto.setDateMod(entity.getDateMod());
        dto.setEmail(entity.getEmail());
        dto.setPass(entity.getPass());
        dto.setRole(RoleMapper.toDto(entity.getRole()));
        return dto;
    }
}
