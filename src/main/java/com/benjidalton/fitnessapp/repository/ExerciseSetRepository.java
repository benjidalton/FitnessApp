package com.benjidalton.fitnessapp.repository;

import com.benjidalton.fitnessapp.model.ExerciseSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseSetRepository extends BaseRepository<ExerciseSet, Long> {

    Optional<ExerciseSet> findFirstByWorkoutExerciseExerciseIdOrderByCreatedAtDescIdDesc(Long exerciseId);
    record WorkoutVolumeRow(Long workoutId, LocalDate date, Double volume,Long setCount) {}
    record ExerciseVolumeRow(Long exerciseId, String exerciseName, LocalDate date, Double volume, Long setCount) {}

    @Query(
            """
            select new com.benjidalton.fitnessapp.repository.ExerciseSetRepository$WorkoutVolumeRow(
                w.id, w.date, cast(coalesce(sum(s.reps * s.weight), 0.0) as double), count(s))
            from ExerciseSet s
              join s.workoutExercise we
              join we.workout w
            where we.exercise.id = :exerciseId and w.date >= :start and w.date <= :end
            group by w.id, w.date
            order by w.date desc, w.id desc
            """)
    List<WorkoutVolumeRow> volumePerWorkout(
            @Param("exerciseId") Long exerciseId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query(
            """
            select new com.benjidalton.fitnessapp.repository.ExerciseSetRepository$ExerciseVolumeRow(
                e.id, e.name, w.date, cast(coalesce(sum(s.reps * s.weight), 0.0) as double), count(s))
            from ExerciseSet s
              join s.workoutExercise we
              join we.workout w
              join we.exercise e
              join e.muscles m
            where m.id = :muscleGroupId and w.date >= :start and w.date <= :end
            group by e.id, e.name, w.date
            order by e.name asc, w.date desc
            """)
    List<ExerciseVolumeRow> volumePerExerciseForMuscleGroup(
            @Param("muscleGroupId") Long muscleGroupId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("""
    select sum(s.reps * s.weight)
    from ExerciseSet s
        join s.workoutExercise we
        join we.workout w
    where w.date >= :startDate
    """)
    Double sumVolumeSinceDate(@Param("startDate") LocalDate startDate);
}
