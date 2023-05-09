package org.iesfm.app.controllers;


import org.iesfm.app.dto.ClassDto;
import org.iesfm.app.dto.UserDto;
import org.iesfm.app.dto.mapper.ClassMapper;
import org.iesfm.app.dto.mapper.UserMapper;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.iesfm.app.exceptions.UserNotFoundException;
import org.iesfm.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/user/{email}/{pass}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("email") String email,
            @PathVariable("pass") String pass
    ) {
        UserDto user = null;
        try {
            user = UserMapper.toDtoLogin(userService.checkUser(email, pass));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }


    @GetMapping(path = "/users/{role}")
    public ResponseEntity<List<UserDto>> getAllStudents(
            @RequestParam(value = "class", required = false) String className,
            @RequestParam(value = "subject", required = false) String subjectName,
            @PathVariable("role") String role
    ) {

        return ResponseEntity.ok(
                userService
                        .findAllStudents(className, subjectName, role)
                        .stream()
                        .map(UserMapper::toDtoInfoWithSUbjectPercentage)
                        .collect(Collectors.toList()));

    }


    @GetMapping(path = "/user/{idUser}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("idUser") Integer idUser
    ) {

        UserDto userDto = null;

        try {
            userDto = UserMapper.toDtoInfoWithSUbjectPercentage(userService.getUser(idUser));
            ;
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userDto);

    }

    @GetMapping(path = "/user/{idUser}/absences")
    public ResponseEntity<UserDto> getUserWithAbsences(
            @PathVariable("idUser") Integer idUser
    ) {

        UserDto userDto = null;

        try {
            userDto = UserMapper.toDtoAbsences(userService.getUser(idUser));

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userDto);

    }

    @PostMapping("/user/{idUserCre}")
    public ResponseEntity<Void> addClass(
            @Valid @RequestBody UserDto userDto,
            @PathVariable("idUserCre") Integer idUser

    ){
        UserEntity entity = null;

        try {
            entity = userService.addUser(UserMapper.toEntity(userDto, LocalDate.now()), idUser);
        } catch (EntityExistsException | IncorrectUserException e) {
            
            //Incorrect user deberia lanzar el estatus no autorizado no un conflict(para el resto de controllers deberia ser igual)
            
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
