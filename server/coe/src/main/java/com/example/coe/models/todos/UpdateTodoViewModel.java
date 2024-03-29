package com.example.coe.models.todos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTodoViewModel {
    @NotBlank
    private int userId;
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime completedAt;
}
