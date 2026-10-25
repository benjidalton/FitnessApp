package com.benjidalton.fitnessapp.dto;

import com.benjidalton.fitnessapp.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public final class Responses {
    private Responses() {}

    public record Message(String message) {}

    public record MuscleGroupDto(Long id, String name) {
        public static MuscleGroupDto from(MuscleGroup m) {
            return new MuscleGroupDto(m.getId(), m.getName()
            );
        }
    }

    public record MuscleGroupVolumeDto(Long id, String name, Double volume) {}

    public record ExerciseDto(Long id, String name, String description, List<MuscleGroupDto> muscles) {
        public static ExerciseDto from(Exercise e) {
            return new ExerciseDto(e.getId(), e.getName(), e.getDescription(), e.getMuscles().stream().map(MuscleGroupDto::from).toList());
        }
    }

    /** Exercise plus the weight / rest of its most recent set. */
    public record ExerciseWithLastSetDto(
            Long id, String name, String description, List<MuscleGroupDto> muscles, Double weight, Integer restSeconds) {
        public static ExerciseWithLastSetDto from(Exercise e, ExerciseSet last) {
            return new ExerciseWithLastSetDto(
                    e.getId(), e.getName(), e.getDescription(), e.getMuscles().stream().map(MuscleGroupDto::from).toList(),
                    last == null ? null : last.getWeight(), last == null ? null : last.getRestSeconds());
        }
    }

    public record SetDto(
            Long id, Long workoutExerciseId, int reps, Double weight, Integer restSeconds, LocalDateTime createdAt) {
        public static SetDto from(ExerciseSet s) {
            return new SetDto(
                    s.getId(), s.getWorkoutExercise().getId(), s.getReps(), s.getWeight(), s.getRestSeconds(), s.getCreatedAt());
        }
    }

    public record WorkoutExerciseDto(
            Long id, Long workoutId, Long exerciseId, int position, String notes, ExerciseDto exercise, List<SetDto> sets) {
        public static WorkoutExerciseDto from(WorkoutExercise we) {
            return new WorkoutExerciseDto(
                    we.getId(), we.getWorkout().getId(), we.getExercise().getId(), we.getPosition(), we.getNotes(),
                    ExerciseDto.from(we.getExercise()), we.getSets().stream().map(SetDto::from).toList());
        }
    }

    public record WorkoutDto(
            Long id, LocalDate date, String notes, LocalDateTime createdAt, List<WorkoutExerciseDto> exercises) {
        public static WorkoutDto from(Workout w) {
            return new WorkoutDto(
                    w.getId(), w.getDate(), w.getNotes(), w.getCreatedAt(),
                    w.getExercises().stream().map(WorkoutExerciseDto::from).toList());
        }
    }

    public record BodyCompositionDto(
            Long id, double weightLbs, Double bodyFatPercentage, Double bmi, Double leanBodyMassLbs,
            LocalDateTime createdAt) {
        public static BodyCompositionDto from(BodyComposition b) {
            return new BodyCompositionDto(
                    b.getId(), b.getWeightLbs(), b.getBodyFatPercentage(), b.getBmi(), b.getLeanBodyMassLbs(), b.getCreatedAt());
        }
    }

    // ----- dashboard / analytics -----

    public record HistoryEntry(Long workoutId, LocalDate date, List<String> muscleGroup) {}

    public record Dashboard(int workoutsThisWeek, double volume, List<HistoryEntry> history) {}

    public record HistoryItem(
            Long workoutId, LocalDate date, List<String> muscleGroup, List<String> exerciseSummaries) {}

    public record WorkoutHistory(int totalWorkouts, List<HistoryItem> historyData) {}

    public record GraphPoint(
            Long workoutId, String exerciseName, Long exerciseId, List<String> muscleGroups, Long setId, int reps,
            Double weight, double totalVolume, Integer restSeconds, LocalDate workoutDate, LocalDateTime createdAt) {}

    public record AllGraphData(int totalWorkouts, List<GraphPoint> graphData) {}

    public record WorkoutGraphData(Long workoutId, LocalDate workoutDate, String notes, List<GraphPoint> graphData) {}

    public record ExerciseVolumePoint(
            Long workoutId, LocalDate workoutDate, String exerciseName, double totalVolume, long setCount) {}

    public record VolumeHistoryPoint(LocalDate workoutDate, double totalVolume, long setCount) {}

    public record ExerciseVolumeHistory(Long exerciseId, String exerciseName, List<VolumeHistoryPoint> volumeHistory) {}

    public record RadarPoint(String muscleGroup, double totalVolume) {}

    private static List<MuscleGroupDto> muscles(Exercise e) {
        return e.getMuscles().stream()
                .sorted(Comparator.comparing(MuscleGroup::getId))
                .map(MuscleGroupDto::from)
                .toList();
    }

    public record WorkoutHistoryDto(
            Long workoutId,
            LocalDate date,
            List<String> muscleGroup
    ) {}

    public record DashboardMetricsDto(
            long workoutsThisWeek,
            double volume,
            List<WorkoutHistoryDto> history
    ) {}


}
