package com.benjidalton.fitnessapp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;

import java.util.*;

@Entity
@Table(name = "exercises")
@Setter
@Getter
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 140)
    private String name;

    @Column(length = 150)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "exercise_muscle",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id"))
    private Set<MuscleGroup> muscles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "exercise")
    private List<WorkoutExercise> workoutExercises = new ArrayList<>();

    protected Exercise() {}

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
