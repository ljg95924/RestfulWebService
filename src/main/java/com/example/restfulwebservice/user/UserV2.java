package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value={"password"}) //웹에서 무시
@JsonFilter("UserInfoV2")
public class UserV2 extends User {
    private String grade;

}
