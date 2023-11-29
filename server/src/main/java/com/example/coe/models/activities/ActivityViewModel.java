package com.example.coe.models.activities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityViewModel {

    private int id;
    private int todoId;
    private String title;
    private String description;


}
