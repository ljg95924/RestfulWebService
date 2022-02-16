package com.example.restfulwebservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value={"password"}) //웹에서 무시
//@JsonFilter("UserInfo")
public class User {
    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해주세요.")
    private String name;
    @Past
    private Date joinDate;

    //@JsonIgnore //웹에서 무시됨
    private String password;
    //@JsonIgnore
    private String ssn;
}
