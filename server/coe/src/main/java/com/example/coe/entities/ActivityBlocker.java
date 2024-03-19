package com.example.coe.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "activity_blockers")
@Getter
public class ActivityBlocker {
    @Id
    @SequenceGenerator(name = "activity_blockers_sequence", sequenceName = "activity_blockers_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocker_id", updatable = false, nullable = false)
    private Blocker blocker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", updatable = false, nullable = false)
    private Activity activity;

    @Column(name = "time_spent", nullable = false)
    private int timeSpent;
}