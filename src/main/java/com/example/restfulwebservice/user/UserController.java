package com.example.restfulwebservice.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;  //의존성주입이라는 방법으로 사용

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users") //Get으로 처리
    public List<User> retrieveAllUsers() {
        return service.findeALl();
    }

    // GET /user/1 or/user/10 -> 1이나 10 등 문자열로 전달됨
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        return service.findOne(id);
    }

    @PostMapping("/users") //Post로 처리
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
