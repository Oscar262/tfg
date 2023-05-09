package org.iesfm.app.controllers;

import org.iesfm.app.dto.AbsenceDto;
import org.iesfm.app.dto.mapper.AbsenceMapper;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.IncorrectDataExpected;
import org.iesfm.app.exceptions.IncorrectDateException;
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

    @GetMapping(path = "/absences")
    public ResponseEntity<List<AbsenceDto>> getAbsences(
            @RequestParam(value = "studentName", required = false) String studentName,
            @RequestParam(value = "studentSurname", required = false) String studentSurname,
            @RequestParam(value = "subject", required = false) String subjectName
    ) {
        
        //las ausencias deberian salir ordenadas por fecha, para ordenarlas se deberia ir al dao y desde alli 
        //cambiar la query del metodo o crear una nueva que tambien ordene las ausencias, para que al verlas en la app
        //aparezcan en orden segun su fecha

        return ResponseEntity.ok(
                absenceService
                        .findAllAbsences(studentName, studentSurname, subjectName)
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
           
        } catch (UserNotFoundException e1){
            
            return ResponseEntity.status(HttpStatus.NOTFOUND).build();
            
        } catch (IncorrectDateException | IncorrectDataExpected e) {
            
            //deberian ser codigos diferentes, para poder diferenciar incorrect data de las excepciones provenientes de las fechas incorrect data 
            
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
