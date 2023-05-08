package org.iesfm.app.controllers;

import org.iesfm.app.dto.ClassDto;
import org.iesfm.app.dto.SubjectDto;
import org.iesfm.app.dto.mapper.ClassMapper;
import org.iesfm.app.dto.mapper.SubjectMapper;
import org.iesfm.app.entity.ClassEntity;
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

@RestController
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping(path = "/subject")
    public List<SubjectEntity> findAll() {
        return subjectService.findAll();
    }

    @GetMapping(path = "/subject/{id}")
    public SubjectEntity findById(@PathVariable int id) {
        return subjectService.getSubject(id);
    }

   // @PostMapping(path = "/subject")
   // public SubjectEntity save(@RequestBody SubjectEntity subjectEntity) {
   //     return subjectService.save(subjectEntity);
   // }

    @DeleteMapping(path = "/subject/{id}")
    public void deleteById(@PathVariable int id) {
        subjectService.deleteById(id);
    }


     @PostMapping("subject/{idUserCre}")
    public ResponseEntity<Void> addSubject(
             @Valid @RequestBody SubjectDto subjectDto,
             @PathVariable("idUserCre") Integer idUser
     ){
         SubjectEntity entity = null;

         try {
             entity = subjectService.addSubject(SubjectMapper.toEntity(subjectDto, LocalDate.now()), idUser);
         } catch (EntityExistsException | IncorrectUserException e) {
             return ResponseEntity.status(HttpStatus.CONFLICT).build();
         }
         return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
