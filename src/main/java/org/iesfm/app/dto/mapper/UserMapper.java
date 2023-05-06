package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.AbsenceDto;
import org.iesfm.app.dto.ClassDto;
import org.iesfm.app.dto.SubjectDto;
import org.iesfm.app.dto.UserDto;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.service.UserService;

import java.util.ArrayList;
import java.util.List;

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

        List<AbsenceDto> absenceDtos = new ArrayList<>();

        List<SubjectDto> subjectDtos = new ArrayList<>();
        List<ClassDto> classDto = new ArrayList<>();

        for (ClassEntity classEntity : entity.getClassEntities()) {
            ClassDto classDto1 = ClassMapper.toUserDto(classEntity);
            classDto.add(classDto1);

        }
        for (SubjectEntity subject : entity.getSubjectList()) {
            SubjectDto subjectDto = SubjectMapper.toDtoInfo(subject);
            subjectDtos.add(subjectDto);
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
        dto.setAbsenceList(absenceDtos);

        dto.setSubjectList(subjectDtos);


        dto.setClassList(classDto);


        return dto;
    }


    public static UserDto toDtoAbsences(UserEntity entity) {

        UserDto dto = new UserDto();

        List<AbsenceDto> absenceDtos = new ArrayList<>();

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
        dto.setAbsenceList(absenceDtos);
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

        List<SubjectDto> subjectDtos = new ArrayList<>();
        List<ClassDto> classDto = new ArrayList<>();
        for (ClassEntity classEntity : entity.getClassEntities()) {
            ClassDto classDto1 = ClassMapper.toUserDto(classEntity);
            classDto.add(classDto1);

        }


        for (SubjectEntity subject : entity.getSubjectList()) {
            SubjectDto subjectDto = SubjectMapper.toDtoInfo(subject);
            subjectDtos.add(subjectDto);
        }

        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setFirstSurname(entity.getFirstSurname());
        user.setSecondSurname(entity.getSecondSurname());
        user.setEmail(entity.getEmail());
        user.setSubjectList(subjectDtos);


        user.setClassList(classDto);
        return user;
    }


    public static UserDto toDtoCreateAbsences(UserEntity entity) {
        UserDto user = new UserDto();


        List<SubjectDto> subjectDtos = new ArrayList<>();

        for (SubjectEntity subject : entity.getSubjectList()) {
            SubjectDto subjectDto = SubjectMapper.toDtoInfo(subject);
            subjectDtos.add(subjectDto);
        }

        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setFirstSurname(entity.getFirstSurname());
        user.setSecondSurname(entity.getSecondSurname());
        user.setEmail(entity.getEmail());
        user.setSubjectList(subjectDtos);

        return user;
    }


    public static UserDto toDtoInfoWithSUbjectPercentage(UserEntity entity) {
        UserDto user = new UserDto();
        UserService service = new UserService();

        List<SubjectDto> subjectDtos = new ArrayList<>();
        List<ClassDto> classDto = new ArrayList<>();
        for (ClassEntity classEntity : entity.getClassEntities()) {
            ClassDto classDto1 = ClassMapper.toUserDto(classEntity);
            classDto.add(classDto1);

        }

        service.getAbsencePercentage(entity, subjectDtos);

        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setFirstSurname(entity.getFirstSurname());
        user.setSecondSurname(entity.getSecondSurname());
        user.setEmail(entity.getEmail());
        user.setSubjectList(subjectDtos);


        user.setClassList(classDto);
        return user;
    }




    public static UserDto toDtoLogin(UserEntity entity) {
        UserDto user = new UserDto();

        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setFirstSurname(entity.getFirstSurname());
        user.setSecondSurname(entity.getSecondSurname());
        user.setEmail(entity.getEmail());
        user.setRole(RoleMapper.toDto(entity.getRole()));
        return user;
    }

}
