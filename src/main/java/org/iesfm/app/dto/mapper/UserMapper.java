package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.*;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.UserClassSubject;
import org.iesfm.app.entity.UserEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserMapper {

    public static UserEntity toEntity(UserDto userDto) {

        UserEntity user = new UserEntity();

        List<AbsenceEntity> absences = new ArrayList<>();
        for (AbsenceDto absenceDto : userDto.getAbsenceList()) {

            absences.add(AbsenceMapper.toEntityInfo(absenceDto));
        }

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setFirstSurname(userDto.getFirstSurname());
        user.setSecondSurname(userDto.getSecondSurname());
        user.setDateCre(userDto.getDateCre());
        user.setDateMod(userDto.getDateMod());
        user.setUsuCre(userDto.getUserCre());
        user.setUsuMod(userDto.getUserMod());
        user.setEmail(userDto.getEmail());
        user.setPass(userDto.getPass());
        user.setRole(RoleMapper.toEnity(userDto.getRole()));
        user.setAbsenceList(absences);

        return user;
    }

    public static UserDto toDto(UserEntity entity) {

        UserDto dto = new UserDto();

        List<ClassDto> classDto = new ArrayList<>();
        List<AbsenceDto> absenceDtos = new ArrayList<>();
        List<SubjectDto> subjectDtos = new ArrayList<>();

        for (UserClassSubject userClassSubject : entity.getRelation()) {
            classDto.add(ClassMapper.toUserDto(userClassSubject.getClassEntity()));
        }
        for (UserClassSubject userClassSubject : entity.getRelation()) {
            subjectDtos.add(SubjectMapper.toUserDto(userClassSubject.getSubject()));
        }
        for (AbsenceEntity absenceEntity : entity.getAbsenceList()) {
            absenceDtos.add(AbsenceMapper.toUserDto(absenceEntity));
        }

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFirstSurname(entity.getFirstSurname());
        dto.setSecondSurname(entity.getSecondSurname());
        dto.setDateCre(entity.getDateCre());
        dto.setDateMod(entity.getDateMod());
        dto.setUserCre(entity.getUsuCre());
        dto.setUserMod(entity.getUsuMod());
        dto.setEmail(entity.getEmail());
        dto.setPass(entity.getPass());
        dto.setRole(RoleMapper.toDto(entity.getRole()));
        dto.setClassList(classDto);
        dto.setAbsenceList(absenceDtos);
        dto.setSubjectList(subjectDtos);
        return dto;
    }

    public static UserEntity toEntityInfo(UserDto userDto) {
        UserEntity user = new UserEntity();

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setFirstSurname(userDto.getFirstSurname());
        user.setSecondSurname(userDto.getSecondSurname());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static UserDto toDtoInfo(UserEntity entity) {
        UserDto user = new UserDto();

        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setFirstSurname(entity.getFirstSurname());
        user.setSecondSurname(entity.getSecondSurname());
        user.setEmail(entity.getEmail());
        return user;
    }
}
