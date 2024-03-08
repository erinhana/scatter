package com.example.coe.models.todos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTodoViewModel {
    @NotNull
    private int userId;
    private String description;
    private LocalDate deadline;
    private LocalDateTime completedAt;
}
