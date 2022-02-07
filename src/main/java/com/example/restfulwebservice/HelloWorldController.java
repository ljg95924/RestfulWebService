package com.example.restfulwebservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    // GET
    // /hello-world(endpoint)
    // @RequestMapping(method=RequestMethod.GET, path="/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    // alt + enter
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloworldBean() {
        return new HelloWorldBean("Hello World");
    }

    //Path Variable
    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloworldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s" , name));
    }

}
