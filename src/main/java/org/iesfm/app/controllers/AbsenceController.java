package org.iesfm.app.controllers;

import org.iesfm.app.dto.AbsenceDto;
import org.iesfm.app.dto.mapper.AbsenceMapper;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.UserEntity;
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
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
