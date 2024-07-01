package com.example.coe.models.todos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoViewModel {

    private int id;
    private int userId;
    private String description;
    private LocalDate deadline;


}
