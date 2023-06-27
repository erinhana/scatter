package com.example.coe.models.activities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDetailViewModel {

    private int id;
    private int todoId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int timeSpent;

    public ActivityDetailViewModel(int id, int todoId, String title, String description) {
    }
}
