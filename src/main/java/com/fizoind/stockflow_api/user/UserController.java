package com.fizoind.stockflow_api.user;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserCreateDto> createUser(@RequestBody UserCreateDto userCreateDto) {
         return new ResponseEntity<>(userService.createUser(userCreateDto), HttpStatusCode.valueOf(200));
    }
}
