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

/**
 * En esta clase se controla la informacion que se envia al cliente o que se recibe de el para las clases
 */
@RestController
public class ClassController {

    @Autowired
    private ClassService classService;

    /**
     * En este metodo se envia al cliente la lista de clases que tiene un profesor
     * @param idTeacher  el id del profesor para el que se buscaran las clases
     * @return devuelve la lista de clases
     */
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

    /**
     * En este metodo se envia desde el cliente una nueva clase para crearla en base de datos
     * @param classDto es la clase que se envia desde el cliente
     * @param idUser es el administrador que crea la nueva clase
     * @return devuelve diferentes codigos HTTP dependiendo de si se ha podido crear o no la nueva clase en base de datos
     */
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

    /**
     * Se envia al cliente todas las clases que existen creadas en base de datos
     * @return devuelve la lista con todas las clases
     */
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
