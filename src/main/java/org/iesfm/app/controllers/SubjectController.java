package org.iesfm.app.controllers;

import org.iesfm.app.dto.SubjectDto;
import org.iesfm.app.dto.mapper.SubjectMapper;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.iesfm.app.service.SubjectService;
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
 * En esta clase se controla la informacion que se envia al cliente o que se recibe de el para las asignaturas
 */
@RestController
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * En este metodo se envia al cliente una lista con todas las asignaturas que existen en base de datos
     * @return devuelve la lista
     */
    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectDto>> getSubjects() {

        return ResponseEntity.ok(
                subjectService
                        .findAll()
                        .stream()
                        .map(SubjectMapper::toDtoName)
                        .collect(Collectors.toList()));


    }

    /**
     * En este metodo se recibe desde el cliente una nueva asignatura para almacenar en base de datos
     * @param subjectDto es la nueva asignatura que se guardara en base de datos
     * @param idUser es el administrador que genera la nueva asignatura
     * @return devuelve diferentes codigos HTTP dependiendo de si se ha podido guardar o no la nueva asignatura
     */
    @PostMapping("subject/{idUserCre}")
    public ResponseEntity<Void> addSubject(
            @Valid @RequestBody SubjectDto subjectDto,
            @PathVariable("idUserCre") Integer idUser
    ) {
        SubjectEntity entity = null;

        try {
            entity = subjectService.addSubject(SubjectMapper.toEntity(subjectDto, LocalDate.now()), idUser);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IncorrectUserException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
