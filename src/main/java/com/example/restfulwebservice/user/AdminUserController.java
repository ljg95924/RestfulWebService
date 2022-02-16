package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "password");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    // GET /admin/user/1 -> /admin/v1/users/1
    // @GetMapping("/v1/users/{id}")
    // @GetMapping(value = "/users/{id}/", params = "version=1") //Request Parameter로 버전 관리
    // @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1") // Header 로 버전 관리
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json") // Produce로 관리 (Header를 통해)
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
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

    // GET /admin/user/1 -> /admin/v2/users/1
    // @GetMapping("/v2/users/{id}")
    // @GetMapping(value = "/users/{id}/",params = "version=2") //Request Parameter로 버전 관리
    // @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2") // Header 로 버전 관리
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json") // produce로 관리 (Header를 통해)
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> User2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);
        return mapping;
    }
}
