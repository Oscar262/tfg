package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.ClassDto;
import org.iesfm.app.dto.SubjectDto;
import org.iesfm.app.dto.UserDto;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ClassMapper {

    public static ClassDto toUserDto(ClassEntity entity) {

        ClassDto classDto = new ClassDto();

        Set<UserDto> teachers = new HashSet<>();

        for (UserEntity user : entity.getUserEntities()) {
            if (user.getRole().getName().equalsIgnoreCase("Teacher")) {
                    teachers.add(UserMapper.toDtoLogin(user));
                    break;
            }
        }

        classDto.setId(entity.getId());
        classDto.setName(entity.getName());
        classDto.setTeachers(teachers);

        return classDto;
    }

    public static ClassDto toDtoToFindClass(ClassEntity entity) {

        ClassDto classDto = new ClassDto();

        Set<UserDto> student= new HashSet<>();

        for (UserEntity user : entity.getUserEntities()) {
            if (user.getRole().getName().equalsIgnoreCase("Student")) {
                    student.add(UserMapper.toDtoCreateAbsences(user));
            }
        }

        classDto.setId(entity.getId());
        classDto.setName(entity.getName());
        classDto.setStudents(student);

        return classDto;
    }

/*
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
*/
    public static ClassEntity toEntity(ClassDto dto, LocalDate localDate) {
        ClassEntity entity = new ClassEntity();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDateCre(localDate);
        entity.setUserCre(UserMapper.toEntity(dto.getUserCre()));

        return entity;
    }


    public static ClassDto toDtoName(ClassEntity entity) {
        ClassDto classDto = new ClassDto();

        classDto.setId(entity.getId());
        classDto.setName(entity.getName());

        return classDto;
    }


}
