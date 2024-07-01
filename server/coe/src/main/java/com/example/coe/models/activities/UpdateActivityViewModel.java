package com.example.coe.models.activities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateActivityViewModel {
    @NotNull
    private int id;
    private int todoId;
    @NotNull
    @Size(min = 5, max = 100)
    private String title;
    @NotNull
    @Size(min = 5, max = 100)
    private String description;
    @NotNull
    private int timeSpent;
}
