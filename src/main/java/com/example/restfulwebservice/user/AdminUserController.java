package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin") //users을 호출할때 앞에 admin을 붙혀 호출됨
public class AdminUserController {
    private UserDaoService service;  //의존성주입이라는 방법으로 사용

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users") //Get으로 처리
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = service.findALl();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "password");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    // GET /user/1 or/user/10 -> 1이나 10 등 문자열로 전달됨
    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "password", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);
        return mapping;
    }

}
