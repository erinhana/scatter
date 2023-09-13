package com.example.coe.models.todos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime deadline;
}
