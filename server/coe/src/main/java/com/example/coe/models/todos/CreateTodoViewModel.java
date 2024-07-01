package com.example.coe.models.todos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTodoViewModel {
    @NotNull
    private Integer userId;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate deadline;
}
