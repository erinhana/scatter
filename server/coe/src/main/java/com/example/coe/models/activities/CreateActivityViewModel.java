package com.example.coe.models.activities;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateActivityViewModel {

    @NotNull
    private int todoId;
    private String title;
    private String description;

}
