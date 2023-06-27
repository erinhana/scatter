package com.example.coe.models.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailViewModel {

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private int numberOfTodosCreated;
    private int numberOfTodosInProgress;
    private int numberOfTodosCompleted;
    private int numberOfActivityBlockers;

}
