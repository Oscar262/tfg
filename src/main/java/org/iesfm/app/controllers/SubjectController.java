package org.iesfm.app.controllers;

import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return subjectService.findByid(id);
    }

    @PostMapping(path = "/subject")
    public SubjectEntity save(@RequestBody SubjectEntity subjectEntity) {
        return subjectService.save(subjectEntity);
    }

    @DeleteMapping(path = "/subject/{id}")
    public void deleteById(@PathVariable int id) {
        subjectService.deleteById(id);
    }
}
