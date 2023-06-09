package org.iesfm.app.controllers;

import org.iesfm.app.dto.ClassDto;
import org.iesfm.app.dto.mapper.ClassMapper;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.iesfm.app.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/classes/{idTeacher}")
    public ResponseEntity<List<ClassDto>> getClasses(
            @PathVariable("idTeacher") Integer idTeacher
    ) {
        return ResponseEntity.ok(
                classService
                        .findAllClassesByTeacher(idTeacher)
                        .stream()
                        .map(ClassMapper::toDtoToFindClass)
                        .collect(Collectors.toList()));

    }

    @PostMapping("/class/{idUserCre}")
    public ResponseEntity<Void> addClass(
            @Valid @RequestBody ClassDto classDto,
            @PathVariable("idUserCre") Integer idUser

    ) {
        ClassEntity entity = null;

        try {
            entity = classService.addClass(ClassMapper.toEntity(classDto, LocalDate.now()), idUser);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IncorrectUserException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/classesList")
    public ResponseEntity<List<ClassDto>> getAllClasses() {

        return ResponseEntity.ok(
                classService
                        .findAll()
                        .stream()
                        .map(ClassMapper::toDtoName)
                        .collect(Collectors.toList()));


    }

}
