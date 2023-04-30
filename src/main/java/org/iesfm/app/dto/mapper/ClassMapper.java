package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.ClassDto;
import org.iesfm.app.dto.UserClassSubjectDto;
import org.iesfm.app.dto.UserDto;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.UserClassSubject;
import org.iesfm.app.entity.UserEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassMapper {
    public static ClassDto toUserDto(ClassEntity entity) {

        ClassDto classDto = new ClassDto();

        Set<UserDto> students = new HashSet<>();
        Set<UserDto> teachers = new HashSet<>();

        for (UserClassSubject userClassSubject : entity.getRelation()) {
            switch (userClassSubject.getUser().getRole().getName()) {
                case "Student":
                    students.add(UserMapper.toDtoInfo(userClassSubject.getUser()));
                    break;
                case "Teacher":
                    teachers.add(UserMapper.toDtoInfo(userClassSubject.getUser()));
                    break;
            }
        }

        classDto.setId(entity.getId());
        classDto.setName(entity.getName());
        classDto.setStudents(students);
        classDto.setTeachers(teachers);

        return classDto;
    }


    public static ClassDto toDto(ClassEntity classEntity) {
        ClassDto classDto = new ClassDto();

        Set<UserDto> students = new HashSet<>();
        Set<UserDto> teachers = new HashSet<>();

        for (UserClassSubject userClassSubject : classEntity.getRelation()) {
            switch (userClassSubject.getUser().getRole().getName()) {
                case "Student":
                    students.add(UserMapper.toDtoInfo(userClassSubject.getUser()));
                    break;
                case "Teacher":
                    teachers.add(UserMapper.toDtoInfo(userClassSubject.getUser()));
                    break;
            }
        }

        classDto.setId(classEntity.getId());
        classDto.setName(classEntity.getName());
        classDto.setUserCre(UserMapper.toDtoInfo(classEntity.getUserCre()));
        classDto.setUserMod(UserMapper.toDtoInfo(classEntity.getUserMod()));
        classDto.setDateCre(classEntity.getDateCre());
        classDto.setDateMod(classEntity.getDateMod());
        classDto.setTeachers(teachers);
        classDto.setStudents(students);
        return classDto;
    }

    public static ClassEntity classEntity(ClassDto dto){
        ClassEntity entity = new ClassEntity();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setUserCre(UserMapper.toEntityInfo(dto.getUserCre()));
        entity.setUserMod(UserMapper.toEntityInfo(dto.getUserMod()));
        entity.setDateCre(dto.getDateCre());
        entity.setDateMod(dto.getDateMod());

        return entity;
    }
}
