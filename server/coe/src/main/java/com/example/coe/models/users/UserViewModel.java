package com.example.coe.models.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserViewModel {

    private int id;
    private String emailAddress;
    private String firstName;
    private String lastName;

}
