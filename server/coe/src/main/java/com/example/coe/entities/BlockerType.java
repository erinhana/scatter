package com.example.coe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "blocker_types")
@Getter
@Setter
@NoArgsConstructor
public class BlockerType {
    @Id
    @SequenceGenerator(name = "blocker_types_sequence", sequenceName = "blocker_types_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blocker_types_sequence")
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "description", nullable = false)
    private String description;
}