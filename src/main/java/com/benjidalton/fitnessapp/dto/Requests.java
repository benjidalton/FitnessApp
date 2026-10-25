package com.benjidalton.fitnessapp.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

public class Requests {
    private Requests() {}

    public record ExerciseCreate(
            @NotBlank(message = "Exercise name is required") String name,
            String description,
            @JsonAlias("muscle_group_ids") List<Long> muscleGroupIds) {}

    public record ExerciseUpdate(String name, String description) {}

    public record MuscleGroupCreate(@NotBlank(message = "Muscle group name is required") String name) {}

    public record SetCreate(
            @NotNull(message = "reps is required") @Positive(message = "reps must be positive") Integer reps,
            Double weight,
            @JsonAlias("rest_seconds") Integer restSeconds) {}

    public record SetUpdate(Integer reps, Double weight, @JsonAlias("rest_seconds") Integer restSeconds) {}

    public record WorkoutExerciseCreate(
            @NotNull(message = "exercise_id is required") @JsonAlias("exercise_id") Long exerciseId,
            Integer position,
            String notes,
            @Valid List<SetCreate> sets) {}

    public record WorkoutCreate(LocalDate date, String notes, @Valid List<WorkoutExerciseCreate> exercises) {}

    public record WorkoutUpdate(LocalDate date, String notes) {}

    public record BodyCompositionCreate(
            @NotNull(message = "weight_lbs is required") @JsonAlias("weight_lbs") Double weightLbs,
            @JsonAlias("body_fat_percentage") Double bodyFatPercentage,
            Double bmi,
            @JsonAlias("lean_body_mass_lbs") Double leanBodyMassLbs) {}

    public record BodyCompositionUpdate(
            @JsonAlias("weight_lbs") Double weightLbs,
            @JsonAlias("body_fat_percentage") Double bodyFatPercentage,
            Double bmi,
            @JsonAlias("lean_body_mass_lbs") Double leanBodyMassLbs) {}

    public record MuscleGroupVolumeRequestDTO(
            LocalDate startDate,
            LocalDate endDate
    ) {}
}
