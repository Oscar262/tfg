package org.iesfm.app.controllers;

import org.iesfm.app.dto.ClassDto;
import org.iesfm.app.dto.mapper.ClassMapper;
import org.iesfm.app.dto.mapper.UserMapper;
import org.iesfm.app.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/classes/{idTeacher}")
    public ResponseEntity<List<ClassDto>> getClasses(
            @PathVariable("idTeacher") Integer idTeacher
    ){
        return ResponseEntity.ok(
                classService
                        .findAllClassesByTeacher(idTeacher)
                        .stream()
                        .map(ClassMapper::toDtoToFindClass)
                        .collect(Collectors.toList()));

    }
}
