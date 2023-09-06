package com.example.coe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "blockers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Blocker {
    @Id
    @SequenceGenerator(name = "blockers_sequence", sequenceName = "blockers_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blockers_sequence")
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

}



//    user_id INTEGER NOT NULL,
//    blocker_type_id INTEGER NOT NULL,
//    CONSTRAINT blockers_blocker_types_blocker_type_id_fk FOREIGN KEY (blocker_type_id)
//    REFERENCES blocker_types (id),
//    CONSTRAINT blockers_users_user_id_fk FOREIGN KEY (user_id)
//    REFERENCES users (id)