package org.iesfm.app.controllers;

import org.iesfm.app.exceptions.NotAdminException;
import org.iesfm.app.service.StudentService;
import org.iesfm.app.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;


    @GetMapping(path = "/login/{idLogin}/{email}/{pass}")
    public ResponseEntity<Boolean> getUsser(
            @PathVariable("idLogin") Integer id,
            @PathVariable("email") String email,
            @PathVariable("pass") String pass
    ) {
        switch (id) {
            case 1:
                if (studentService.checkUsser(email, pass)) {
                    return ResponseEntity.ok().build();
                } else
                    return ResponseEntity.notFound().build();

            case 2:
                try {
                    if (teacherService.checkUsser(email, pass)) {
                        return ResponseEntity.ok().build();
                    } else return ResponseEntity.notFound().build();
                } catch (NotAdminException e) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }

            default:
                return ResponseEntity.notFound().build();
        }


    }
}
