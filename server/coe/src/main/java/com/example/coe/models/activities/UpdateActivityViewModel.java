package com.example.coe.models.activities;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateActivityViewModel {
    @NotBlank
    private int id;
    private int todoId;
    private String title;
    private String description;
    private int timeSpent;
}
