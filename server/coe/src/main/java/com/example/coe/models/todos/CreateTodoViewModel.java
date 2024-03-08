package com.example.coe.models.todos;

import jakarta.validation.constraints.Min;
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
    @Min(1)
    private Integer userId;
    @NotBlank
    private String description;
    private LocalDate deadline;
}
