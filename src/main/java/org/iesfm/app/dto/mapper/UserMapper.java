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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * En esta clase se encuentran todos los metodos que convierten las entidades en dtos para los usuarios
 */
public class UserMapper {

    /**
     * En este metodo se transforma un dto de usuario en una entidad de usuario
     * @param userDto es el dto que se transformara
     * @return devuelve la entidad de usuario
     */
    public static UserEntity toEntity(UserDto userDto) {

        UserEntity user = new UserEntity();


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
        user.setClassEntities(ClassMapper.toEntityList(userDto.getClassList()));
        user.setSubjectList(SubjectMapper.toEntityList(userDto.getSubjectList()));
        user.setRole(RoleMapper.toEnity(userDto.getRole()));

        return user;
    }

    /**
     * En este metodo se transformara una entidad de usuario en un dto de usuario
     * @param entity es el usuario que se transformara
     * @return devuelve el dto de usuario
     */
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

    /**
     * En este metodo se transformara una entidad de usuario en un dto de usuario
     * @param entity es el usuario que se transformara
     * @return devuelve el dto de usuario
     */
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

    /**
     * En este metodo se transformara una entidad de usuario en un dto de usuario
     * @param entity es el usuario que se transformara
     * @return devuelve el dto de usuario
     */
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

    /**
     * En este metodo se transformara una entidad de usuario en un dto de usuario
     * @param entity es el usuario que se transformara
     * @return devuelve el dto de usuario
     */
    public static UserDto toDtoLogin(UserEntity entity) {
        UserDto user = new UserDto();

        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setFirstSurname(entity.getFirstSurname());
        user.setSecondSurname(entity.getSecondSurname());
        user.setEmail(entity.getEmail());
        user.setPass(entity.getPass());
        user.setUserCre(entity.getUsuCre());
        user.setDateCre(entity.getDateCre());
        user.setRole(RoleMapper.toDto(entity.getRole()));
        return user;
    }

    /**
     * En este metodo se transforma un dto de usuario en una entidad de usuario
     * @param userDto es el dto que se transformara
     * @return devuelve la entidad de usuario
     */
    public static UserEntity toEntityChangePass(UserDto userDto) {
        UserEntity entity = new UserEntity();

        entity.setId(userDto.getId());
        entity.setName(userDto.getName());
        entity.setFirstSurname(userDto.getFirstSurname());
        entity.setSecondSurname(userDto.getSecondSurname());
        entity.setEmail(userDto.getEmail());
        entity.setPass(userDto.getPass());
        entity.setUsuCre(userDto.getUserCre());
        entity.setDateCre(userDto.getDateCre());
        entity.setUsuMod(userDto.getId());
        entity.setDateMod(LocalDate.now());
        entity.setRole(RoleMapper.toEnity(userDto.getRole()));
        return entity;

    }
}
