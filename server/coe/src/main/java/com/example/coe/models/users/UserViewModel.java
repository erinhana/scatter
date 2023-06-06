package com.example.coe.models.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserViewModel {

    private int id;
    private String email;
    private String firstName;
    private String lastName;

}
