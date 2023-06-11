package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.ClassDto;
import org.iesfm.app.dto.UserDto;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.UserEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * En esta clase se encuentran todos los metodos que convierten las entidades en dtos para las clases
 */
public class ClassMapper {

    /**
     * En este metodo se transforma una entidad de clase en un dto de clase
     * @param entity es la entidad a transformar
     * @return devuelve el dto con algunos de sus campos
     */
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

    /**
     * En este metodo se transforma una entidad de clase en un dto de clase
     * @param entity es la entidad a transformar
     * @return devuelve el dto con algunos de sus campos
     */
    public static ClassDto toDtoToFindClass(ClassEntity entity) {

        ClassDto classDto = new ClassDto();

        Set<UserDto> student = new HashSet<>();

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

    /**
     * En este metodo se trasnforma un dto de clase en una entidad de clase
     * @param dto es el dto a transformar
     * @param localDate es la fecha en la que se ha creado la clase
     * @return devuelve una entidad de clase
     */
    public static ClassEntity toEntity(ClassDto dto, LocalDate localDate) {
        ClassEntity entity = new ClassEntity();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDateCre(localDate);

        return entity;
    }

    /**
     * En este metodo se transforma una entidad de clase en un dto de clase
     * @param entity es la entidad a transformar
     * @return devuelve el dto con algunos de sus campos
     */
    public static ClassDto toDtoName(ClassEntity entity) {
        ClassDto classDto = new ClassDto();

        classDto.setId(entity.getId());
        classDto.setName(entity.getName());

        return classDto;
    }

    /**
     * En este metodo se transforma una lista de dtos de clases en un conjunto de entidades de clases
     * @param classList es la lista de dtos a transformar
     * @return devuelve el conjunto de entidades
     */
    public static Set<ClassEntity> toEntityList(List<ClassDto> classList) {
        List<ClassEntity> listEntity = new ArrayList<>();

        for (ClassDto dto : classList) {
            listEntity.add(toEntity(dto, LocalDate.now()));
        }
        return new HashSet<>(listEntity);

    }
}
