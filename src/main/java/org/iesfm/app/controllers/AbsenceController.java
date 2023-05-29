package org.iesfm.app.controllers;

import org.iesfm.app.dto.AbsenceDto;
import org.iesfm.app.dto.mapper.AbsenceMapper;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.IncorrectDataExpected;
import org.iesfm.app.exceptions.IncorrectDateException;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.iesfm.app.exceptions.UserNotFoundException;
import org.iesfm.app.service.AbsenceService;
import org.iesfm.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    @GetMapping(path = "/absences/{teacher}/{subjectId}")
    public ResponseEntity<List<AbsenceDto>> getAbsences(
            @PathVariable("teacher") Integer teacher,
            @PathVariable("subjectId") Integer subjectId
    ) {
        
        //las ausencias deberian salir ordenadas por fecha, para ordenarlas se deberia ir al dao y desde alli 
        //cambiar la query del metodo o crear una nueva que tambien ordene las ausencias, para que al verlas en la app
        //aparezcan en orden segun su fecha

        return ResponseEntity.ok(
                absenceService
                        .findAllAbsences(teacher,subjectId)
                        .stream()
                        .map(AbsenceMapper::toUserDto)
                        .collect(Collectors.toList()));

    }

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

            //en este controller se lanzara un 404 si no se encuentra alguno de los usuarios, ya sea el profesor o el alumno,
            //por lo que para este error se debera crear un toast en android en plan: "estudiante no encontrado, pruebe a recargar la
            // pagina y selecione de nuevo al estudiante", o algo similar, si salta el 406, el toast seria: "El contenido introducido
            // no es correcto, compruebe las horas asignadas, el estudiante no puede tener más de 7 horas asignadas a la misma fecha",
            //y para el ultimo, el 422, el toast seria como: "esa fecha no es elegible, pruebe a seleccionar una fecha para este curso escolar"

        } catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IncorrectDataExpected e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }catch (IncorrectDateException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
