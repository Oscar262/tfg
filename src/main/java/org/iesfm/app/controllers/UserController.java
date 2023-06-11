package org.iesfm.app.controllers;


import org.iesfm.app.dto.UserDto;
import org.iesfm.app.dto.mapper.UserMapper;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.*;
import org.iesfm.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * En esta clase se controla la informacion que se envia al cliente o que se recibe de el para los usuarios
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * En este metodo se recibe de cliente informacion para buscar en base de datos un unico usuario
     * @param email es el email que se recibe del cliente
     * @param pass es la contraseña que se recibe del cliente
     * @return devuelve un usuario que este guardado con esa contraseña y correo en base de datos, en caso de que exista
     */
    @GetMapping(path = "/user/{email}/{pass}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("email") String email,
            @PathVariable("pass") String pass
    ) {
        UserDto user = null;
        try {
            user = UserMapper.toDtoLogin(userService.getUserLogin(email, pass));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    /**
     * En este metodo se envia al cliente una lista con todos los usuarios que cumplan ciertas condiciones
     * @param classId es la clase a la que tienen que pertenecer los estudiantes o en la que tienen que dar clase los
     *                profesores para aparecer en esta lista
     * @param subjectId es la asignatura que tienen que impartir o en la que tienen que estar matriculados para aparecer
     *                  en esta lista
     * @param role es el rol que se buscara en base de datos
     * @return devuelve la lista de los usuarios que cumplan las condiciones anteriores
     */
    @GetMapping(path = "/users/{role}/{classId}/{subjectId}")
    public ResponseEntity<List<UserDto>> getAllStudents(
            @PathVariable("classId") Integer classId,
            @PathVariable("subjectId") Integer subjectId,
            @PathVariable("role") Integer role
    ) {

        List<UserDto> students;
        try {
            students = userService
                    .findAllStudents(classId, subjectId, role)
                    .stream()
                    .map(UserMapper::toDtoLogin).collect(Collectors.toList());
        } catch (EmptytListException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(students);
    }

    /**
     * En este metodo se envia desde el cliente informacion para buscar en base de datos
     * @param idUser es el id del usuario que se quiere buscar en base de datos
     * @return devuelve el usuario en caso de ser encontrado en la base de datos
     */
    @GetMapping(path = "/user/{idUser}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("idUser") Integer idUser
    ) {

        UserDto userDto = null;

        try {
            userDto = UserMapper.toDtoInfoWithSUbjectPercentage(userService.getUser(idUser));

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userDto);

    }

    /**
     * En este metodo se envia al cliente un usuario con una lista de sus ausencias
     * @param idUser es el usuario para el que se buscara la lista de ausencias
     * @return devuelve al usuario
     */
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

    /**
     * En este metodo se enviara desde el cliente la informacion necesaria para crear un nuevo usuario en base de datos
     * @param userDto es el usuario que se generara en base de datos
     * @param idUser es el id del usuario que crea el nuevo usuario en base de datos
     * @return devuelve diferentes codigos HTTP dependiendo de si se ha podido crear o no al nuevo usuario
     */
    @PostMapping("/user/{idUserCre}")
    public ResponseEntity<Void> addUser(
            @Valid @RequestBody UserDto userDto,
            @PathVariable("idUserCre") Integer idUser

    ) {
        UserEntity entity = null;

        try {
            entity = userService.addUser(UserMapper.toEntity(userDto), idUser);
        } catch (IncorrectUserException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ClassListException | IncorrectDataExpected e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (ClassListExceptionStudent e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * En este metodo se envia desde el cleinte informacion para cambiar la contraseña a un usuario
     * @param userDto es el usuario con la contraseña ya cambiada que se debe actualizar en base de datos
     * @return devuelve el codigo HTTP 200 cuando se puede actualizar la contraseña
     */
    @PutMapping(path = "/user/newPass")
    public ResponseEntity<Void> changePass(
            @Valid @RequestBody UserDto userDto
    ) {

        UserEntity entity = null;

        entity = userService.updateUser(UserMapper.toEntityChangePass(userDto));

        return ResponseEntity.ok().build();
    }

}
