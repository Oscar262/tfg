package org.iesfm.app.controllers;

import org.iesfm.app.dto.AbsenceDto;
import org.iesfm.app.dto.mapper.AbsenceMapper;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.exceptions.IncorrectDataExpected;
import org.iesfm.app.exceptions.IncorrectDateException;
import org.iesfm.app.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * En esta clase se controla la informacion que se envia al cliente o que se recibe de el para las ausencias
 */
@RestController
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    /**
     * En este metodo se envia al cliente una lista con las ausencias que un profesor a creado para una asignatura concreta
     * @param teacher es el profesor con el que se ha iniciado sesion en la aplicacion, y el que se utilizara para buscar
     *                las ausencias
     * @param subjectId es la asignatura que se utilizara para la busqueda
     * @return devuelve la lista con las ausencias encontradas
     */
    @GetMapping(path = "/absences/{teacher}/{subjectId}")
    public ResponseEntity<List<AbsenceDto>> getAbsences(
            @PathVariable("teacher") Integer teacher,
            @PathVariable("subjectId") Integer subjectId
    ) {

        return ResponseEntity.ok(
                absenceService
                        .findAllAbsences(teacher, subjectId)
                        .stream()
                        .map(AbsenceMapper::toUserDto)
                        .collect(Collectors.toList()));

    }

    /**
     * En este metodo se envia desde el cliente una ausencia para eliminarla en base de datos
     * @param absenceId es el id de la ausencia que se eliminara en base de datos
     * @return en este caso lo que se devolvera sera un codigo HTTP en este caso una codigo 200 si se ha eliminado correctamente
     */
    @DeleteMapping(path = "/absence/{absenceId}")
    public ResponseEntity<Void> deleteAbsence(
            @PathVariable("absenceId") Integer absenceId
    ) {

        absenceService.delete(absenceId);

        return ResponseEntity.ok().build();


    }

    /**
     * En este metodo se enviara desde el cliente la informacion desde el cliente para generar una nueva ausencia en la
     * base de datos
     * @param absence es la ausencia que se generara en la base de datos
     * @param idSubject es el id de la asignatura a la que se ha faltado, y para la que se generara la ausencia
     * @param idStudent es el id del estudiante que ha faltado, y para el que se generara la ausencia
     * @param idTeacher es la id del profesor que genera la falta
     * @return devuelve un codigo HTTP dependiendo de si se ha podido generar de forma correcta o si ha habido algun problema
     */
    @PostMapping(path = "/absence/{idSubject}/{idStudent}/{idTeacher}")
    public ResponseEntity<Void> newAbsence(
            @Valid @RequestBody AbsenceDto absence,
            @PathVariable("idSubject") Integer idSubject,
            @PathVariable("idStudent") Integer idStudent,
            @PathVariable("idTeacher") Integer idTeacher
    ) {
        AbsenceEntity entity = null;

        try {
            entity = absenceService.addAbsence(AbsenceMapper.toEntity(absence), idSubject, idStudent, idTeacher);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IncorrectDataExpected e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (IncorrectDateException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
