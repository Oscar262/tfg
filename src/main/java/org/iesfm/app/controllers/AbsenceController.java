package org.iesfm.app.controllers;

import org.iesfm.app.dto.AbsenceDto;
import org.iesfm.app.dto.mapper.AbsenceMapper;
import org.iesfm.app.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    //puedes quitar los dtos y poner simplemente string y controlarlo desde java

}
