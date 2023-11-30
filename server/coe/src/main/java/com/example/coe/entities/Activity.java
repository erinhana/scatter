package com.example.coe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Activity {
    @Id
    @SequenceGenerator(name = "activities_sequence", sequenceName = "activities_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activities_sequence")
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @Column(name = "time_spent", nullable = false)
    private int timeSpent;
}


//    todo_id INTEGER NOT NULL,
//    time_spent INTEGER NOT NULL,
//    CONSTRAINT activity_todos_todo_id_fk FOREIGN KEY (todo_id)
//    REFERENCES todos (id)