package com.benjidalton.fitnessapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "exercise_sets")
@Setter
@Getter
public class ExerciseSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    private WorkoutExercise workoutExercise;

    @Column(nullable = false)
    private int reps;

    private Double weight;

    @Column(name = "rest_seconds")
    private Integer restSeconds;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected ExerciseSet() {}

    public ExerciseSet(int reps, Double weight, Integer restSeconds) {
        this.reps = reps;
        this.weight = weight;
        this.restSeconds = restSeconds;
    }

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
