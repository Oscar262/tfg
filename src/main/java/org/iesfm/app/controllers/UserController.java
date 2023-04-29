package org.iesfm.app.controllers;


import org.iesfm.app.dto.UserDto;
import org.iesfm.app.dto.mapper.UserMapper;
import org.iesfm.app.exceptions.UserNotFoundException;
import org.iesfm.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(path = "/user/{email}/{pass}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("email") String email,
            @PathVariable("pass") String pass
    ) {
        UserDto user = null;
        try {
            user = UserMapper.toDto(service.checkUser(email, pass));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }
}
